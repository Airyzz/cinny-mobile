
pub mod navigation {
    pub mod back_button;
}

pub const MESSAGE_BACK_BUTTON: &str = "TAURI_EVENT_BACK_BUTTON";

#[derive(Clone, serde::Serialize)]
pub struct AppMessage {
}