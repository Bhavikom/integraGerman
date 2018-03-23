
# jaypalbhai ##############################################################

# Removing logging code


# The -dontwarn option tells ProGuard not to complain about some artefacts in the Scala runtime


-ignorewarnings
#-keep class * {
#    public private *;
#}

# Retain declared checked exceptions for use by a Proxy instance.
#-keepclasseswithmembers class * {
#    @retrofit2.http.* <methods>;
#    }




#---------------------------------------------------------------------------------------------------
# General
-keep class de.mateco.integrAMobile.model_logonsquare**{*;}
-keep class de.mateco.integrAMobile.model**{*;}
-keep public class android.content.Intent**{*;}
-keep class com.google.android.email
# This is a configuration file for ProGuard.
# http://proguard.sourceforge.net/index.html#manual/usage.html

-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-verbose
-dontwarn javax.xml.**
-dontwarn com.sun.**

# Optimization is turned off by default. Dex does not like code run
# through the ProGuard optimize and preverify steps (and performs some
# of these optimizations on its own).
-dontoptimize
-dontpreverify

# If you want to enable optimization, you should include the
# following:
# -optimizations !code/simplification/arithmetic,!code/simplification/cast,!field/*,!class/merging/*
# -optimizationpasses 5
# -allowaccessmodification
#
# Note that you cannot just include these flags in your own
# configuration file; if you are including this file, optimization
# will be turned off. You'll need to either edit this file, or
# duplicate the contents of this file and remove the include of this
# file from your project's proguard.config path property.

#-ignorewarnings
-keepdirectories
-keepattributes *Annotation*
-libraryjars <java.home>/lib/rt.jar(java/**,javax/**)


-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgent
-keep public class * extends android.preference.Preference
-keep public class * extends android.support.v4.app.Fragment
-keep public class * extends android.app.Fragment
-keep public class com.android.vending.licensing.ILicensingService
-keep class com.actionbarsherlock.** { *; }
-keep interface com.actionbarsherlock.** { *; }
-keep class org.** { *; }
-keep class com.sun.** { *; }
-keep class org.odata4j.** { *; }
-keep class javax.** { *; }
-keep public class com.android.vending.licensing.ILicensingService

# For native methods, see http://proguard.sourceforge.net/manual/examples.html#native
-keepclasseswithmembernames class * {
    native <methods>;
}

-keep public class * extends android.view.View {
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
    public void set*(...);
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}

# For enumeration classes, see http://proguard.sourceforge.net/manual/examples.html#enumerations
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}

-keepclassmembers class **.R$* {
    public static <fields>;
}

# The support library contains references to newer platform versions.
# Don't warn about those in case this app is linking against an older
# platform version.  We know about them, and they are safe.
-dontwarn android.support.**

#ACRA specifics
# we need line numbers in our stack traces otherwise they are pretty useless
-renamesourcefileattribute SourceFile
-keepattributes SourceFile,LineNumberTable

# ACRA needs "annotations" so add this...
-keepattributes *Annotation*

# keep this class so that logging will show 'ACRA' and not a obfuscated name like 'a'.
# Note: if you are removing log messages elsewhere in this file then this isn't necessary
-keep class org.acra.ACRA {
        *;
}

# keep this around for some enums that ACRA needs
-keep class org.acra.ReportingInteractionMode {
   *;
}

# keep this otherwise it is removed by ProGuard
-keep public class org.acra.ErrorReporter

# keep this otherwise it is removed by ProGuard
-keep public class org.acra.ErrorReporter

# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Basic proguard rules
-optimizations !code/simplification/arithmetic
-keepattributes Annotation
-keepattributes InnerClasses
-keepattributes EnclosingMethod
-keep class *.R$
-keep class com.squareup.okhttp.** { *; }

-dontskipnonpubliclibraryclasses
-forceprocessing
-optimizationpasses 5
-overloadaggressively

# Removing logging code
-assumenosideeffects class android.util.Log {
public static *** d(...);
public static *** v(...);
public static *** i(...);
public static *** w(...);
public static *** e(...);
}

# The -dontwarn option tells ProGuard not to complain about some artefacts in the Scala runtime

-dontwarn android.support.**
-dontwarn android.app.Notification
-dontwarn org.apache.**
-dontwarn com.google.common.**
-dontwarn org.w3c.dom.**
-dontwarn com.squareup.picasso.**
-dontwarn com.android.**

-keep public class com.google.android.gms.**
-dontwarn com.google.android.gms.**

-ignorewarnings
#-keep class * {
#    public private *;
#}

# Retain declared checked exceptions for use by a Proxy instance.
#-keepclasseswithmembers class * {
#    @retrofit2.http.* <methods>;
#    }

-keepclassmembernames,allowobfuscation interface * {
    @retrofit2.http.* <methods>;
}

-keep class com.fasterxml.** { *; }
-dontwarn com.fasterxml.**

-keep class com.google.ads.** {*;}

#---------------------------------------------------------------------------------------------------
# General
-keepattributes Signature
-keepattributes Exceptions
-keepattributes *Annotation*
-keepattributes EnclosingMethod

# Javascript
-keepattributes JavascriptInterface
-keepclassmembers class * {
    @android.webkit.JavascriptInterface <methods>;
}

# Glide
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
    **[] $VALUES;
    public *;
}

# Retrofit
-dontnote retrofit2.Platform # Platform calls Class.forName on types which do not exist on Android to determine platform.
-dontnote retrofit2.Platform$IOS$MainThreadExecutor # Platform used when running on RoboVM on iOS. Will not be used at runtime.
-dontwarn retrofit2.Platform$Java8 # Platform used when running on Java 8 VMs. Will not be used at runtime.

# OkHttp 3
-keep class okhttp3.** { *; }
-keep interface okhttp3.** { *; }
-dontwarn okhttp3.**

# Okio
-keep class sun.misc.Unsafe { *; }
-dontwarn java.nio.file.*
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement
-dontwarn okio.**

# App compat
-keep public class android.support.v7.widget.** { *; }
-keep public class android.support.v7.internal.widget.** { *; }
-keep public class android.support.v7.internal.view.menu.** { *; }
-keep public class * extends android.support.v4.view.ActionProvider {
    public <init>(android.content.Context);
}

# Butterknife
-keep public class * implements butterknife.Unbinder { public <init>(**, android.view.View); }
-keep class butterknife.*
-keepclasseswithmembernames class * { @butterknife.* <methods>; }
-keepclasseswithmembernames class * { @butterknife.* <fields>; }

# Joda time
-dontwarn org.joda.convert.**
-dontwarn org.joda.time.**
-keep class org.joda.time.** { *; }
-keep interface org.joda.time.** { *; }

# Unity
-dontwarn com.androidnative.**
-keep public class com.unity.plugins.** { *; }
-keep class com.unity3d.** { *; }
-keep class bitter.jnibridge.** { *; }
-keep class org.fmod.** { *; }

# Billing
-keep class com.android.vending.billing.**

#Gson
-keep class com.google.gson.** { *; }

-dontwarn java.awt.**
-dontwarn java.beans.Beans
-dontwarn javax.security.**

-keep class javamail.** {*;}
-keep class javax.mail.** {*;}
-keep class javax.activation.** {*;}

-keep class com.sun.mail.dsn.** {*;}
-keep class com.sun.mail.handlers.** {*;}
-keep class com.sun.mail.smtp.** {*;}
-keep class com.sun.mail.util.** {*;}
-keep class mailcap.** {*;}
-keep class mimetypes.** {*;}
-keep class myjava.awt.datatransfer.** {*;}
-keep class org.apache.harmony.awt.** {*;}
-keep class org.apache.harmony.misc.** {*;}

