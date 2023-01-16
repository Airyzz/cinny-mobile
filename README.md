#  Cinny Mobile

Proof of concept for running cinny under the tauri mobile alpha

**Note: Very Experimental**

### Local Development

After following instructions for setting up Android Studio / SDK + Rust

* `git clone --recursive https://github.com/Airyzz/cinny-mobile.git`
* `cd cinny`
* `npm ci`
* `cd ..`
* `npm ci`
* `cargo tauri android init`

To start local dev server, run:
* `cargo tauri android dev`
