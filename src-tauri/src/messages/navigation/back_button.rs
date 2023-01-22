use tauri::async_runtime::{Sender, Receiver, Mutex};
use std::sync::Arc;
use tauri::{AppHandle, Manager};
use lazy_static::lazy_static;

use crate::messages::{AppMessage, self};

lazy_static! {
    #[derive(Debug)]
    pub static ref BACK_BUTTON_SENDER: Mutex<Option<Sender<u8>>> = Mutex::new(None);
}

pub fn listen(app_handle: AppHandle, mut receiver: Receiver<u8>) {
    tauri::async_runtime::spawn(async move {
        loop {
            if let Some(_message) = receiver.recv().await {
                println!("Received Back Button");
                app_handle.emit_all(messages::MESSAGE_BACK_BUTTON, AppMessage {  }).unwrap();
            }
        };
    });
  }