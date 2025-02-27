# Specific ProGuard rules

# Keep important attributes
-keepattributes *Annotation*
-keepattributes SourceFile,LineNumberTable
-keepattributes Signature
-keepattributes Exceptions

# # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # #
# # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # #
# HORNS-APP
# # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # #
# # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # #
# Api Models
-keepclassmembers class com.yesferal.hornsapp.app.framework.retrofit.entity.* {
    *;
}
-keepclassmembers class com.yesferal.hornsapp.core.domain.entity.* {
    *;
}
-keepclassmembers class com.yesferal.hornsapp.core.domain.entity.drawer.* {
    *;
}
-keepclassmembers class com.yesferal.hornsapp.core.domain.navigator.Parameters {
    *;
}

# Navigation Component
-keepnames class com.yesferal.hornsapp.app.presentation.common.base.ParcelableViewData
-keepclassmembers class com.yesferal.hornsapp.app.presentation.common.base.ParcelableViewData {
    *;
}

# ViewModel
-keepclassmembers public class * extends androidx.lifecycle.ViewModel { public <init>(...); }


# Debug
-keepattributes SourceFile, LineNumberTable



# # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # #
# # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # #
# OKIO
# # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # #
# # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # #
# Animal Sniffer compileOnly dependency to ensure APIs are compatible with older versions of Java.
-dontwarn org.codehaus.mojo.animal_sniffer.*

# Retrofit rules
# Keep generic signature of Call, Response (R8 full mode strips signatures from non-kept items).
 -keep,allowobfuscation,allowshrinking interface retrofit2.Call
 -keep,allowobfuscation,allowshrinking class retrofit2.Response

 # With R8 full mode generic signatures are stripped for classes that are not
 # kept. Suspend functions are wrapped in continuations where the type argument
 # is used.
 -keep,allowobfuscation,allowshrinking class kotlin.coroutines.Continuation
