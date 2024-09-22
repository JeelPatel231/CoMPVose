{ pkgs ? import <nixpkgs> {} }:

(pkgs.buildFHSEnv {
  name = "devbox";
  targetPkgs = pkgs: (with pkgs; [
    jdk21
    libGL
    libGLU
    mpv

    vulkan-tools
    vulkan-loader
  ]);

  multiPkgs = pkgs: (with pkgs; [
  ]);
  runScript = "bash";
}).env

# LD_LIBRARY_PATH=$LD_LIBRARY_PATH:/usr/lib:/usr/lib64 LC_NUMERIC=C ./gradlew run