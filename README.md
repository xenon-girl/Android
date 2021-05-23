
# Android App To Order Tent Accessories Online
## About the project
____

Automate the process to orderthe tent accessories like Mattresses, Chairs, Crockery, etc. on rent for all types of events.
<br />

## Installation
____
Clone this repository and import into **Android Studio**
```bash
git clone https://github.com/xenon-girl/Android.git
```
Clone the repo, open the project in Android Studio, hit "Run". **Done!**

<br />

## Demo of the app
____
<br />
&nbsp;&nbsp;
<img src="https://user-images.githubusercontent.com/48390770/119239492-07187100-bb67-11eb-88d9-be5b3187cf69.jpg" 
alt="drawing" width="250" height="500"/> 
&nbsp;&nbsp;&nbsp;
<img src="https://user-images.githubusercontent.com/48390770/119240017-c589c500-bb6a-11eb-9523-e33a69b32c18.gif" 
alt="drawing" width="250" height="500"/>
&nbsp;&nbsp;&nbsp;
<img src="https://user-images.githubusercontent.com/48390770/119239031-d84ccb80-bb63-11eb-8d9c-c35e40060f3b.jpg" 
alt="drawing" width="250" height="500"/> 
<br />
 
<br />

## External Dependencies
____
- Cirle Image (de.hdodenhof:circleimageview:3.1.0)
- SpotsDialog (com.github.d-max:spots-dialog:1.1@aar)
- Butter Knife (com.jakewharton:butterknife:10.2.1)
- Firebase (com.google.firebase:firebase-database:19.3.0)
- Firebase Analytics (com.google.firebase:firebase-analytics:17.4.2)
- Glide (com.github.bumptech.glide:glide:4.11.0)

<br />

## Project Architecture
----
```

Android/OrderApp/app/src/main/
├── java
│   ├── adapter/*
│   ├── callback/*
│   ├── common/*
│   ├── eventbus/*
│   ├── interface/*
│   ├── model/*
│   ├── viewholder/*
│   ├── ui/*
│   ├── Home.java
│   ├── MainActivity.java
│   ├── SignIn.java
│   ├── SignUp.java
│   └── SplashScreenActivity.java
├── assets/fonts
│   └── Nabila.ttf
├── res/*
│   ├── layout/*
│   │   ├── activity_main.xml
│   │   ├── activity_home.xml
│   │   ├── activity_splash_screen.xml
│   │   └── and many more ...         
│   ├── values/*
│   ├── navigation/*
│   ├── drawable/*
│   └── anim/*
└── AndroidManifest.xml 

```

## Maintainers
This project is mantained by:
* [Swati Deshwal](http://github.com/xenon-girl)

<br />

## Contributing
____
1. Fork it
2. Create your feature branch (git checkout -b my-new-feature)
3. Commit your changes (git commit -m 'Add some feature')
4. Push your branch (git push origin my-new-feature)
5. Create a new Pull Request