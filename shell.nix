{ pkgs ? import <nixpkgs> {} }:

(pkgs.buildFHSEnv {
  name = "devbox";
  targetPkgs = pkgs: (with pkgs; [
    jdk21
    libGL
    libGLU
    mpv
    yt-dlp

    vulkan-tools
    vulkan-loader
  ]);

  multiPkgs = pkgs: (with pkgs; [
  ]);

  runScript = "bash";

  profile = ''
    export LD_LIBRARY_PATH=$LD_LIBRARY_PATH:/usr/lib:/usr/lib64
    export LC_NUMERIC=C
    export JAVA_HOME=${pkgs.jdk21}/lib/openjdk
  '';

}).env
