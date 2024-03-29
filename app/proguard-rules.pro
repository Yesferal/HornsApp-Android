# Specific ProGuard rules

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