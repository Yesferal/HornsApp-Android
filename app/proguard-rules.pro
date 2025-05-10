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
-keepclassmembers class com.yesferal.hornsapp.core.domain.entity.render.* {
    *;
}
-keepclassmembers class com.yesferal.hornsapp.core.domain.entity.util.* {
    *;
}
-keepclassmembers class com.yesferal.hornsapp.core.domain.common.* {
    *;
}
-keepclassmembers class com.yesferal.hornsapp.core.domain.navigator.Parameters {
    *;
}

# Navigation Component
-keepnames class com.yesferal.hornsapp.app.framework.navigator.ParcelableViewData
-keepclassmembers class com.yesferal.hornsapp.app.framework.navigator.ParcelableViewData {
    *;
}

# Keep Android components
#-keepclassmembers public class * extends android.app.Activity
#-keepclassmembers public class * extends android.app.Application
#-keepclassmembers public class * extends android.app.Service
#-keepclassmembers public class * extends android.content.BroadcastReceiver
#-keepclassmembers public class * extends android.content.ContentProvider
#-keepclassmembers public class * extends android.app.backup.BackupAgentHelper
#-keepclassmembers public class * extends android.preference.Preference
#-dontnote com.android.vending.licensing.ILicensingService

# Keep custom views
-keep public class * extends android.view.View {
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
    public void set*(...);
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
