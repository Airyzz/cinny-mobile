
use jni::JNIEnv;

use jni::objects::{GlobalRef, JClass, JObject, JString};

use jni::sys::{jbyteArray, jint, jlong, jstring};

#[no_mangle]
pub extern "system" fn Java_xyz_airyz_cinny_app_MainActivity_myNativeMethod(
    env: JNIEnv,
    _class: JClass,
    input: JString,
) -> jstring {
    // First, we have to get the string out of java. Check out the `strings`
    // module for more info on how this works.
    let input: String = env
        .get_string(input)
        .expect("Couldn't get java string!")
        .into();

    // Then we have to create a new java string to return. Again, more info
    // in the `strings` module.
    let output = env
        .new_string(format!("Hello, {}!", input))
        .expect("Couldn't create java string!");
    // Finally, extract the raw pointer to return.
    output.into_raw()
}
