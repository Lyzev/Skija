# Skija

**Minecraft Version:** 1.21.4

This project is an example implementation of integrating the Skija graphics library with Minecraft to enhance rendering capabilities. It demonstrates how to use Skija for advanced rendering techniques within the Minecraft environment.

In this example, we render the blurred version of the Minecraft scene using Skija.

## Features

- **OpenGL State Management:** The project includes utilities for saving and restoring OpenGL states to ensure compatibility with Minecraft's rendering pipeline.
- **Skija Integration:** Utilizes Skija for rendering, providing advanced graphics capabilities.
- **Framebuffer Handling:** Ensures Skija is reinitialized when the window is resized.
- **Image Handling:** Provides utilities to convert Minecraft scene to Skija images.

## Usage

To run the project, use the following Gradle tasks:

- **Run Client:** `./gradlew runClient`
- **Run Client with RenderDoc:** `./gradlew runClient + RenderDoc`

## License

This project is licensed under the AGPL-3.0 License. See the [LICENSE](LICENSE) file for details. 

Some parts of this project are inspired by the [imgui-java](https://github.com/SpaiR/imgui-java) project.

## Acknowledgements

- [imgui-java](https://github.com/SpaiR/imgui-java) for inspiration and code references.
- [Skija](https://github.com/HumbleUI/Skija) for providing the bindings to the Skia graphics library.
- [EldoBug](https://github.com/EldoDebug) for adding bindings to Skija to allow using GL Textures as Skija images.
