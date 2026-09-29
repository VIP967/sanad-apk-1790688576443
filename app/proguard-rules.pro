-optimizerenabled
-optimizations !code/simplification/arithmetic,!code/simplification/cast,!field/*,!class/merging/*

-keepattributes *Annotation*
-keepattributes Signature
-keepattributes InnerClasses
-keepattributes EnclosingMethod

-keep class com.example.advancedcalculator.** { *; }

-dontwarn org.mozilla.javascript.**