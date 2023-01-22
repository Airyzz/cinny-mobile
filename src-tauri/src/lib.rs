use tokio::{sync::{
  mpsc::{channel, Receiver, Sender},
  Mutex,
  futures
}};
use lazy_static::*;
use tauri::{App, Manager, AppHandle};
mod java;
mod messages;

use messages::navigation::back_button::{self, BACK_BUTTON_SENDER};


#[cfg(mobile)]
mod mobile;
#[cfg(mobile)]
pub use mobile::*;

use crate::messages::AppMessage;

pub type SetupHook = Box<dyn FnOnce(&mut App) -> Result<(), Box<dyn std::error::Error>> + Send>;

#[derive(Default)]
pub struct AppBuilder {
  setup: Option<SetupHook>,
}

impl AppBuilder {
  pub fn new() -> Self {
    Self::default()
  }

  #[must_use]
  pub fn setup<F>(mut self, setup: F) -> Self
  where
    F: FnOnce(&mut App) -> Result<(), Box<dyn std::error::Error>> + Send + 'static,
  {
    self.setup.replace(Box::new(setup));
    self
  }

  

  pub fn run(self) {

    let setup = self.setup;
    tauri::Builder::default()
      .setup(move |app| {


        if let Some(setup) = setup {
          (setup)(app)?;
        }

        let app_handle = app.handle();
        
        let (sender, receiver) = tauri::async_runtime::channel::<u8>(1);

        *BACK_BUTTON_SENDER.blocking_lock() = Some(sender);
        
        back_button::listen(app_handle, receiver);
        
        Ok(())
      })
      .run(tauri::generate_context!())
      .expect("error while running tauri application");
  }
}
