# Pool Floating Tools

បង្កើតដោយ **AppForge** · វេបសាយ → App · `com.example.poolfloatingoverlay`

## របៀបបង្កើត APK (ឥតគិតថ្លៃ ~៣ នាទី)

1. បង្កើត repo ថ្មីនៅ GitHub (Public ឬ Private ក៏បាន)។
2. Upload ឯកសារទាំងអស់ក្នុង ZIP នេះទៅ root របស់ repo
   (ត្រូវឃើញ `settings.gradle.kts` និងថត `.github/` នៅ root)។
3. ចូល **Actions** → *Build APK* → រង់ចាំ ~២–៤ នាទី។
4. ចុចលើ run ដែលជោគជ័យ → **Artifacts** → ទាញយក `poolfloatingtools-apk.zip`។
5. ពន្លាត → បានឯកសារ `app-debug.apk` → ដំឡើងលើទូរស័ព្ទ
   (ត្រូវបើក "Install unknown apps" សម្រាប់ browser ដែលអ្នកប្រើ)។

## បើ build មិនជោគជ័យ

- Java/Gradle ខុស៖ workflow ប្រើ JDK 17 + Gradle 8.7 ស្រាប់ មិនត្រូវកែទេ។
- បើអ្នកប្តូរ `compileSdk` ត្រូវប្តូរ `buildToolsVersion`/AGP ឱ្យត្រូវគ្នា។
- Log កំហុសនៅ Actions → ចុច step ពណ៌ក្រហម។

## ចំណាំ

- APK នេះជា **debug** — ដំឡើងលើទូរស័ព្ទបាន តែដាក់លើ Play Store មិនបានទេ
  (ត្រូវការ release keystore + AAB)។
- បើ App ប្រើអ៊ីនធឺណិត ត្រូវមាន `<uses-permission android:name="android.permission.INTERNET" />`
  ដែលបានដាក់ឱ្យរួចហើយ។
- Package name: `com.example.poolfloatingoverlay` · Version 1.0 (1)
