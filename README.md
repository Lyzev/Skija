# Skija

**Minecraft Version:** 1.21.4 | **Mod Loader:** Fabric Only

This project is an example implementation of integrating the Skija graphics library with Minecraft to enhance rendering capabilities. It demonstrates how to use Skija for advanced rendering techniques within the Minecraft environment.

**⚠️ Note:** This mod is built specifically for Fabric and is not compatible with Forge or NeoForge.

In this example, we render the blurred version of the Minecraft scene using Skija.

## Features

- **OpenGL State Management:** The project includes utilities for saving and restoring OpenGL states to ensure compatibility with Minecraft's rendering pipeline.
- **Skija Integration:** Utilizes Skija for rendering, providing advanced graphics capabilities.
- **Framebuffer Handling:** Ensures Skija is reinitialized when the window is resized.
- **Image Handling:** Provides utilities to convert Minecraft scene to Skija images.

## Mod Loader Support

**This mod currently supports Fabric only.**

- **Supported:** Fabric Loader 0.16.14+
- **Not Supported:** Forge, NeoForge, Quilt

## Usage

To run the project, use the following Gradle tasks:

- **Run Client:** `./gradlew runClient`
- **Run Client with RenderDoc:** `gradlew.bat "runClient + RenderDoc"` (requires Windows and RenderDoc installed in the default location `C:\Program Files\RenderDoc\renderdoccmd.exe`)

## FAQ

### Can this mod be used with Forge?

No, this mod is currently designed specifically for Fabric and is not compatible with Forge or NeoForge. The mod uses Fabric-specific features including:
- Fabric mixins system
- Fabric mod loader APIs
- Fabric build toolchain (fabric-loom)

### Will there be Forge support in the future?

Porting to Forge would require significant architectural changes and is not currently planned. However, the core concepts and Skija integration techniques demonstrated in this project could potentially be adapted for Forge-based mods by experienced developers.

### Can I use this with Quilt?

While not explicitly tested, this mod may work with Quilt since Quilt maintains compatibility with many Fabric mods. However, official support is only provided for Fabric.

## License

This project is licensed under the AGPL-3.0 License. See the [LICENSE](LICENSE) file for details. 

Note that this project includes OpenSans font file, which is licensed under [SIL OFL 1.1 License](https://github.com/googlefonts/opensans/blob/main/OFL.txt).

## Acknowledgements

- [imgui-java](https://github.com/SpaiR/imgui-java) for inspiration and code references.
- [Skija](https://github.com/HumbleUI/Skija) for providing the bindings to the Skia graphics library.
- [EldoDebug](https://github.com/EldoDebug) for adding bindings to Skija to allow using GL Textures as Skija images.
