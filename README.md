# cs56-android-getting-started
A CS56 project that describes how to get started with Android Programming in the context of CS56

##[Proceed to the Tutorial](/docs/tutorial/index.md)##

####[How was this tutorial built?](GitTutorialBuilding.md)####

####[pmsosa Legacy Code Project: SmokeSignals](https://github.com/pmsosa/SmokeSignals)####

<h1> Installing Android Studio </h1>
<h6>Last updated: osheaanaya | Summer 2016</h6>

- [Setting up Android Studio for Windows](#0_androidWindows)
- [Setting up Android Studio for Mac](#0_androidMac)
- [Setting up Android Studio for Linux](#0_androidLinux)
- [Sources](#0_sources)

---


<h2 id="0_androidWindows">Setting up Android Studio for Windows</h2>

***Note:* Android Studio is constantly reinventing itself and these steps could easily become deprecated in a near future so we highly recommend that you follow the latest instructions that are found at: https://developer.android.com/sdk/installing/index.html?pkg=studio**

- **Step 1: Install Latest Java JDK:** http://www.oracle.com/technetwork/java/javase/downloads/index.html
	- For Windows you must add java as an enviornment variable Select Start menu > Computer > System Properties > Advanced System Properties. Then open Advanced tab > Environment Variables and add a new system variable JAVA_HOME that points to your JDK folder, for example C:\Program Files\Java\jdk1.8.0_21.

- **Step 2: Download and Install Android Studio + (Android SDK & Emulators):** https://developer.android.com/studio/index.html
- To install Android Studios Proceed as follows:
- 1. Launch the .exe file you downloaded. 
- 2. Follow the setup wizard to install Android Studio and any necessary SDK tools. 

- **Step 3: Adding any necessary SDK packages**
By default, Android Studio will come with all the SDK packages that you need, but if for some reason you don't have them (e.g. You installed the standalone Android Studio). As a minimum when setting up the Android SDK, you should download the latest tools and Android platform:
![Taken from https://developer.android.com/sdk/installing/adding-packages.html](0_sdk.png)

Go into Android Studio and choose **Tools > Android > SDK Manager**

1. Open the Tools directory and select:
	- **Android SDK Tools** 
	- **Android SDK Plataform-tools**
	- **Android SDK Build-tools** (highest vesrsion)
2. Open the first Android X.X folder (the latest version) and select:
	- **SDK Plataform**
	- System image for the emulator, such as **ARM EABI v7a System Image**
3. Open the Extras Folder and select:
	- **Google USB Driver** *(Super important for actual phone testing)*
	- **Android Support Library**
	- **Android Support Repository**
	- **Google Repository**
	- (Optional) **Intel x86 Emulator Accelerator** *(Makes the default emulator a bit more efficient and fast)*
	
