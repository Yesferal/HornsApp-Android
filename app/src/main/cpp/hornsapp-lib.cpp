#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring JNICALL
Java_com_yesferal_hornsapp_MainActivity_secret(
    JNIEnv* env,
    jobject
) {
    std::string secret = "S3CR3T";

    return env->NewStringUTF(secret.c_str());
}