# Example Project Data

Used to test different events.

## Images

Export `.bmp` files from GIMP with 24 bit color (R8 G8 B8) and "Do not write color space information".

## Videos

Use `ffmpeg` to convert videos to expected format.

Converted screen recording with
```shell
ffmpeg -i '.\Walking Around.mp4' -acodec pcm_s16le -pix_fmt yuv420p walking-around-again.avi
```
