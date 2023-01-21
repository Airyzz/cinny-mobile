use tauri::async_runtime::{Sender, Mutex};

use lazy_static::lazy_static;

lazy_static! {
    #[derive(Debug)]
    pub static ref BACK_BUTTON: Mutex<Option<Sender<u8>>> = Mutex::new(None);
}
