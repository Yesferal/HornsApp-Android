# Horns App
This is an Native Android App about concerts at Lima.

## Requirements Analysis
As users, we need to:
1. Discover the newest concerts at Lima
2. Discover the upcoming concerts at Lima
3. Select concerts as favorites, and save them for later
4. Know more about a concert; as social networks, videos, bands, venue and date
5. Discover the bands that will play in a concert, and the detail about them

## Software Design
### Architecture
This app was designed following Clear Architecture and the SOLID principles.
This app has been divided in 3 modules:
1. Domain: Kotlin Module
2. Data: Kotlin Module
3. App: Android Module. This module contains the Presentation and the Framework modules.

### Presentation Module
In this module we tried two different presentation patterns just for academic purposes.
We separated the implementations in two branches.
1. [MVP pattern branch](https://github.com/Yesferal/Hornsapp-Android/tree/mvp-pattern)
2. [MVVM pattern branch](https://github.com/Yesferal/Hornsapp-Android/)

The first presentation pattern that we implement in this project was MVP pattern.
To do it we defined a base contract that contains both `View` & `ActionListener` interfaces,
and then we created an abstract classes: `BaseActivity` & `BasePresenter`.

The second presentation pattern was MVVM pattern. Here we use the Android's ViewModel class,
and we applies the observable pattern to communicate the `ViewModel` with its `View`,
so now we can update the view easily using LiveData.

## Implementation: Getting Started
### Clone the exiting project
This project need Git and AndroidStudio previously installed.
Then you should:

1. Clone the repository, the default branch is staging.
2. Launch AndroidStudio and use the option `Open an existing project`.

### Run the Application
Once you have the project open, make sure you choose the `Debug` build variant.
It is important to say that `Debug` variant will use a online mock as API.
Finally, run the app module.

### Final Approach
This app was made using Kotlin mainly, but we used C++(NDK) too.


## License
```
Copyright 2020 HornsApp Contributors

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

     https://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```