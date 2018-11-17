## Image Pins
A simple HTTP service allowing users to pin images, stream them later
and view others' pins.

### Images
* id (hash)
* image description
* photographer
* date created
* binary data

### Users
* user name
* date joined
* pinned images

### HTTP Requests
* <i>POST /users/{user}</i>
    * returns info of newly created user
    * 201 created

* <i>GET /users/{user}</i>
    * returns user info
    * 200 OK

* <i>DELETE /users/{user}</i>
    * deletes user and pinned images (unlinking if necessary)
    * 200 ok (always succeeds)

* <i>GET /users</i>
    * returns a list of users
    * 200 ok

* <i>PUT /images/{user}</i>
    * returns info of newly created image
    * 201 created

* <i>GET /images/{user}/{image}</i>
    * returns image info
    * 200 ok

* <i>GET /images</i>
    * returns a list of images
    * 200 ok

* <i>DELETE /images/{user}/{image}</i>
    * deletes pinned image for user (unlinking if necessary)
    * 200 ok (always succeeds)

### Data Store
* images and users are stored on the host filesystem
* images in a directory and users in a file
* users are loaded into memory on startup, user file updates are queued
* song info and paths are loaded into memory on startup, file updates
  are queued
* reading songs requires asynchronous I/O

### Build
As per the link below, Intellij can import Play applications as Gradle
projects but the option to create separate modules per source set must
be disabled in order to resolve Gradle dependencies.

https://discuss.gradle.org/t/intellij-play-2-integration/12265