use tokio::{sync::{
  mpsc::{channel, Receiver, Sender},
  Mutex,
  futures
}};

use tauri::{App, Manager};
mod java;
mod messages;
use messages::BACK_BUTTON;

#[cfg(mobile)]
mod mobile;
#[cfg(mobile)]
pub use mobile::*;

pub type SetupHook = Box<dyn FnOnce(&mut App) -> Result<(), Box<dyn std::error::Error>> + Send>;

#[derive(Default)]
pub struct AppBuilder {
  setup: Option<SetupHook>,
}

#[derive(Clone, serde::Serialize)]
struct Payload {
  message: String,
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

        let (sender, mut receiver) = tauri::async_runtime::channel::<u8>(1);

        *BACK_BUTTON.blocking_lock() = Some(sender);
        let app_handle = app.handle();

        tauri::async_runtime::spawn(async move {
          loop {
            if let Some(_message) = receiver.recv().await {
              println!("RECEIVEDDD");
              app_handle.emit_all("tauri-event", Payload { message: "Tauri is awesome!".into() }).unwrap();
            }
          };
        });
        
        Ok(())
      })
      .run(tauri::generate_context!())
      .expect("error while running tauri application");
  }
}
