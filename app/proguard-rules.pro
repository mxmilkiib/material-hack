# Add project specific ProGuard rules here.
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
-keepclassmembers class io.github.mxmilkiib.materialistic.WebFragment$PdfAndroidJavascriptBridge {
   public *;
}

-dontobfuscate
-keep class io.github.mxmilkiib.materialistic.** { *; }
-keep interface io.github.mxmilkiib.materialistic.** { *; }
