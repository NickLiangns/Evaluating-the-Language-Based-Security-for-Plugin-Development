## Evaluating the Language-Based Security for Plugin Development

This repo is for evaluating the language-Based Security for Plugin Development. The developing environment is based on Intellij and employs JAVA mainly.

## Objectives
* Developing IntelliJ plug ins that try to access files, network, other parts of the application that plugin user does not expect.
* Exploring the CVE that take advantage of similar capability- based accesses and implementing them as IntelliJ plugin exploits.
* Analysis of which of these can be addressed by languages that support capability-based module systems like Wyvern.

## Plugin Installation
1. Install plugins manually:
- First git clone the repo to local computer
- Open any one of plugins
- Navigate to <kbd>Preferences</kbd> > <kbd>Plugins</kbd> > <kbd>Install plugin from disk...</kbd>
![localdisk](/screenshots/localdisk.png)
- choose the zip file inside the folder of <kbd>build</kbd> > <kbd>distributions</kbd> > <kbd>snapshot</kbd>
![snapshot](/screenshots/snapshot.png)

2. Install plugins from repo (recommend):
- First git clone the repo to local computer
- Change the configuration to fit the current version of your IDEA
  - Open the file `build.gradle`
  - Edit the version in ```plugins {
    id 'org.jetbrains.intellij' version '1.13.2'
    id 'java'
} ``` to the version of IDEA
- Click the `Gradle ` menu on the right-top side of IDE.
- Run the command `runIde` in the  <kbd>Intellij</kbd> >  <kbd>runIde</kbd> 
- ![runIde](/screenshots/runIde.png)
- Otherwise you can package the plugin again
  - Delete the previous snapshot
  - Run the command `buildPlugin` in the  <kbd>Intellij</kbd> >  <kbd>buildPlugin</kbd>
  - A new zip file will be generated in the folder <kbd>build</kbd> > <kbd>distributions</kbd>
 ![buildPlugin](/screenshots/buildPlugin.png)

3. Install plugins from marketplace (may not compatible with your IDEA):
- Using IDE built-in plugin system on Windows:
  - <kbd>File</kbd> > <kbd>Settings</kbd> > <kbd>Plugins</kbd> > <kbd>Browse repositories...</kbd> > <kbd>Search for each plugin</kbd> > <kbd>Install Plugin</kbd>
- Using IDE built-in plugin system on MacOs:
  - <kbd>Preferences</kbd> > <kbd>Settings</kbd> > <kbd>Plugins</kbd> > <kbd>Browse repositories...</kbd> > <kbd>Search for each plugin</kbd> > <kbd>Install Plugin</kbd>
![marketplace](/screenshots/marketplace.png)

Restart IDE.

