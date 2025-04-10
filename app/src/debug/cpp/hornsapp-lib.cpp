#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring JNICALL
Java_com_yesferal_hornsapp_app_framework_retrofit_ApiConstants_authorization(
        JNIEnv* env,
        jobject /* this */) {

    return env->NewStringUTF("");
}

extern "C" JNIEXPORT jstring JNICALL
Java_com_yesferal_hornsapp_app_framework_retrofit_ApiConstants_baseUrl(
        JNIEnv* env,
        jobject /* this */) {

    return env->NewStringUTF("https://demo8819092.mockable.io/");
}

extern "C" JNIEXPORT jstring JNICALL
Java_com_yesferal_hornsapp_app_framework_adMob_AdUnitIds_concertsBannerAdUnitId(
        JNIEnv* env,
        jobject /* this */) {

    return env->NewStringUTF("ca-app-pub-3940256099942544/6300978111");
}
