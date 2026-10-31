# DonutFinder 0.1 — Fabric 1.21.11

This is **source code**, not a precompiled `.jar`. The GitHub Actions workflow attempts to compile it automatically. It has **not been compiled or tested** in this environment; dependency or API adjustments may be needed.

## Build with GitHub (no Java setup on your PC)
1. Create a GitHub repository.
2. Upload all project files, including the hidden `.github/workflows/build.yml` folder.
3. Open **Actions**, select **Build Fabric Mod**, and run it (or push to `main`).
4. If the workflow succeeds, download the artifact and extract `donutfinder-0.1.0.jar`.
5. If the workflow fails, inspect the build log; this is an unverified prototype.

## Install the mod
1. Install the Fabric Loader for Minecraft Java **1.21.11** from https://fabricmc.net/use/installer/ .
2. Run Minecraft once using the new Fabric profile, then quit.
3. Press Win+R, enter `%appdata%\\.minecraft\\mods` and press Enter. Create `mods` if missing.
4. Put **only** the built `donutfinder-0.1.0.jar` into `mods` (not the source ZIP).
5. Launch the **Fabric 1.21.11** profile.
6. In game, press **F8** to toggle scanning. When 3+ storage block entities are found in a loaded chunk, the mod sends a chat alert and saves it to `.minecraft/config/donutfinder-locations.txt`.

This mod does not read hidden server chunks, and detection may be incomplete due to server packet behavior. Check server rules before use.
