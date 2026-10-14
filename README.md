<h1 align="center">TenantManagementSystem</h1>
<p align="center">
  <em>A property manager's app built with View Binding, Data Binding and Intents.</em>
  <br>
  <br>
  <a href="#overview">Overview</a> ·
  <a href="#screenshots">Screenshots</a> ·
  <a href="#what-it-does">What It Does</a> ·
  <a href="#how-the-screens-connect">How the Screens Connect</a> ·
  <a href="#how-to-run">How to Run</a> ·
  <a href="#project-structure">Project Structure</a> ·
  <a href="#built-with">Built With</a> ·
  <a href="#build-notes">Build Notes</a> ·
  <a href="#author">Author</a>
  <br>
  <img src="https://img.shields.io/badge/version-1.1.0-16a34a" alt="Version 1.1.0">
  <img src="https://img.shields.io/badge/license-UNLICENSED-16a34a" alt="UNLICENSED">
  <img src="https://img.shields.io/badge/platform-Android-16a34a" alt="Android">
  <img src="https://img.shields.io/badge/language-Kotlin-16a34a" alt="Kotlin">
</p>

---

TenantManagementSystem is the Android app built for the BBT 3.2 Mobile Application Development practicals at Strathmore University. It starts with a Login and a Register screen, moves to an Add Tenant screen where a property manager enters a tenant's name, phone number and rent paid, and can hand a saved tenant's number to the phone dialer or open a web page.

The Lesson 6 practicals walk through the progression from a static screen, to View Binding, to Data Binding, then add navigation: multiple Activities, explicit Intents, passing data with `putExtra`, and implicit Intents that ask other apps to do things. The app is as much a demonstration of those techniques as it is a usable form.

## Overview

The app has three Activities. `LoginActivity` is the first screen: it takes an email and password, opens `MainActivity` with an explicit Intent, and links to `RegisterActivity`. `RegisterActivity` validates three fields, then returns to Login carrying the email with `putExtra("EMAIL")`. `MainActivity` is the Add Tenant screen: its layout is a ConstraintLayout that holds a title, three inputs (name, phone, rent), a SAVE button, a CALL TENANT button and a result area.

Kotlin reaches the views through View Binding, and the result area reads a `Tenant` object directly through Data Binding. The point of the exercise is the separation of concerns. `MainActivity` builds a `Tenant` and hands it to the layout, and the layout decides how that `Tenant` is displayed. Changing the display format means editing the `Tenant` class, not the activity.

Practical 2 goes inside the Activity: it logs the Activity lifecycle so the create/pause/stop/destroy order is visible in Logcat, and it saves the last tenant in a `Bundle` so it survives a screen rotation. It then builds the screens that will live inside `MainActivity` as three Fragments — Dashboard, Add Tenant and View Tenants — and maps them in a Navigation Graph. Those Fragments are created and mapped but not shown yet; Practical 3 plugs them into `MainActivity` with a `NavHost`.

## Screenshots

<p align="center">
  <img src="screenshots/login.png" width="300" alt="Login screen">
  <img src="screenshots/register.png" width="300" alt="Register screen">
  <img src="screenshots/login-prefilled.png" width="300" alt="Login screen with the email carried over from Register">
</p>
<p align="center">
  <em>Login, Register, and Login again after Register carried the email across with putExtra.</em>
</p>

<p align="center">
  <img src="screenshots/add-tenant-welcome.png" width="300" alt="Add Tenant screen greeting the logged-in user">
  <img src="screenshots/add-tenant-saved.png" width="300" alt="Add Tenant screen after saving a tenant">
</p>
<p align="center">
  <em>The Add Tenant screen as it opens — greeted with the email carried from Login — and after a tenant is saved through Data Binding.</em>
</p>

<p align="center">
  <img src="screenshots/add-tenant-share.png" width="300" alt="Android share sheet after tapping SHARE TENANT">
</p>
<p align="center">
  <em>SHARE TENANT hands the saved tenant's summary to the Android share sheet through an implicit ACTION_SEND Intent.</em>
</p>

On the Add Tenant screen the name renders in large bold text and the summary is filled in by Data Binding, the three inputs clear on save, CALL TENANT hands the saved number to the dialer, and SHARE TENANT hands the tenant's summary to any app that can send text.

## What it does

- Opens on a Login screen and reaches every view through View Binding, so a wrong id fails at compile time instead of crashing at runtime.
- Validates the Login fields and blocks the move to the dashboard when either is empty.
- Opens `MainActivity` (the Add Tenant screen) with an explicit Intent, then `finish()`es so Back does not return to Login.
- Carries the registered email from Register to Login with `putExtra("EMAIL")` / `getStringExtra`, and uses `FLAG_ACTIVITY_CLEAR_TOP` so Register does not stack a second Login.
- Greets the logged-in user: Login forwards the email to `MainActivity` with `putExtra("EMAIL")`, which shows a *Logged in as …* toast.
- Opens the Strathmore website from the Login screen with an implicit Intent (`ACTION_VIEW` + `Uri.parse`).
- Renders a `Tenant` object through Data Binding with `android:text="@{tenant.summary()}"`.
- Holds tenant data in a `Tenant` data class (`name`, `phone`, `rent`) with a `summary()` method.
- Validates the tenant name: an empty name shows a field error and blocks the save.
- Shows the saved tenant's name in large bold text above the summary, and clears the three inputs after a save.
- Dials the last saved tenant with an implicit Intent (`ACTION_DIAL` + `"tel:$phone"`), warning "Save a tenant first" if nothing is saved yet.
- Logs every Activity lifecycle callback under the `TENANT_LIFECYCLE` tag, so the order Android calls them in is visible in Logcat.
- Saves the last tenant in `onSaveInstanceState()` and restores it in `onCreate()`, so a saved tenant survives a screen rotation.
- Keeps tenants in an in-memory `TenantRepository` object that every screen shares.
- Defines `DashboardFragment`, `AddTenantFragment` and `ViewTenantsFragment`, mapped in `res/navigation/nav_graph.xml` with Safe Args actions (wired into `MainActivity` in Practical 3).

## How the screens connect

```
                    implicit Intent (ACTION_VIEW)
LoginActivity ───────────────────────────────────▶ Browser
   │     ▲
   │     │ explicit Intent + putExtra("EMAIL")
   │     │
   │  RegisterActivity
   │
   │ explicit Intent
   ▼
MainActivity ─── implicit Intent (ACTION_DIAL) ───▶ Phone dialer
```

An explicit Intent names the exact screen to open (`Intent(this, MainActivity::class.java)`). An implicit Intent only describes what you want done, and Android finds an app on the phone that can do it (open a web page, dial a number).

## Lifecycle and saved state

`MainActivity` logs every lifecycle callback under the tag `TENANT_LIFECYCLE`, so the order Android calls them in — `onCreate → onStart → onResume → onPause → onStop → onRestart → onDestroy` — is visible while you use the app (`adb logcat -s TENANT_LIFECYCLE`). `LoginActivity` and `RegisterActivity` log their own lifecycles under the same tag.

Rotating the screen destroys and recreates the Activity, which would wipe the Kotlin variables that hold the last saved tenant. To stop that, `MainActivity` writes the tenant's three fields into the `Bundle` in `onSaveInstanceState()` and reads them back in `onCreate()`, so a saved tenant — and the CALL/SHARE buttons — still work after a rotation. The EditText contents survive on their own, because Android saves and restores any field that has an id.

## Fragments and the navigation graph

The screens that will live inside `MainActivity` are already written as Fragments, ready for Practical 3 to wire up:

- `DashboardFragment` — shows the tenant count and total rent from the shared `TenantRepository`.
- `AddTenantFragment` — the Add Tenant form.
- `ViewTenantsFragment` — the just-added tenant (Data Binding) plus the list of all tenants.

`res/navigation/nav_graph.xml` maps them: `DashboardFragment` is the start destination and has actions to Add Tenant and to View Tenants; Add Tenant has an action to View Tenants; View Tenants takes an optional `newTenant` argument. `Tenant` is now `Serializable`, so it can be passed through that argument, and Safe Args is set up to generate the type-safe navigation classes. Nothing shows these screens yet — Practical 3 adds the `NavHost` and App Bar to `MainActivity`.

## How to run

### Android Studio (recommended)

1. Open the project folder in Android Studio.
2. Let Gradle sync finish.
3. Start an emulator from Device Manager, or connect a device with USB debugging enabled.
4. Select the `app` run configuration and press Run.

### Command line

Build a debug APK:

```bash
./gradlew assembleDebug
```

Install and launch on a running emulator or connected device:

```bash
./gradlew installDebug
adb shell am start -n com.example.tenantmanagementsystemgroupa/.LoginActivity
```

The website link needs an emulator image with a browser (Google Play or Chrome); the dialer link works on any phone image.

## Project structure

```
TenantManagementSystem/
├── app/
│   ├── build.gradle.kts
│   └── src/main/
│       ├── AndroidManifest.xml
│       ├── java/com/example/tenantmanagementsystemgroupa/
│       │   ├── LoginActivity.kt
│       │   ├── RegisterActivity.kt
│       │   ├── MainActivity.kt
│       │   ├── DashboardFragment.kt
│       │   ├── AddTenantFragment.kt
│       │   ├── ViewTenantsFragment.kt
│       │   ├── Tenant.kt
│       │   └── TenantRepository.kt
│       └── res/
│           ├── layout/
│           │   ├── activity_login.xml
│           │   ├── activity_register.xml
│           │   ├── activity_main.xml
│           │   ├── fragment_dashboard.xml
│           │   ├── fragment_add_tenant.xml
│           │   └── fragment_view_tenants.xml
│           ├── navigation/
│           │   └── nav_graph.xml
│           └── values/
│               ├── strings.xml
│               ├── themes.xml
│               └── colors.xml
├── gradle/
│   ├── libs.versions.toml
│   └── wrapper/
├── build.gradle.kts
├── settings.gradle.kts
├── gradle.properties
├── screenshots/
│   ├── login.png
│   ├── register.png
│   ├── login-prefilled.png
│   ├── add-tenant-welcome.png
│   ├── add-tenant-saved.png
│   └── add-tenant-share.png
└── README.md
```

## Built with

- Kotlin 2.2.10
- Android Gradle Plugin 9.1.1
- Gradle 9.3.1
- AndroidX AppCompat 1.7.0
- AndroidX ConstraintLayout 2.2.1
- AndroidX Core KTX 1.13.1
- AndroidX Navigation (Fragment KTX + UI KTX) 2.9.8, Safe Args 2.9.8
- minSdk 24, compileSdk 36, targetSdk 36

## Build notes

Android Studio 2026.1 ships Android Gradle Plugin 9, where Kotlin support is built in. Two consequences for anyone following the original practical guide:

- The Kotlin Android plugin is not applied. Applying it fails with `Cannot add extension with name 'kotlin'`.
- `kotlin-kapt` is not compatible with built-in Kotlin, and Data Binding no longer needs it. Turning on `dataBinding = true` is enough for the binding classes to be generated.
- Safe Args declares its plugin version once in the project `build.gradle.kts` (`apply false`) and switches it on in the module. The plugin and both navigation libraries must all be `2.9.8`.

The project builds clean with `./gradlew assembleDebug`.

## Author

- Allan Ngugi - 191250

## License

UNLICENSED. Coursework submission for BBT 3.2 Mobile Application Development.
