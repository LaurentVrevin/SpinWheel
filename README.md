
# SpinWheel - Animated Spinning Wheel in Jetpack Compose

SpinWheel is a customizable and animated spinning wheel component built using **Jetpack Compose**. The project demonstrates a dynamic wheel that selects random items, with visual effects like segments, arrows, and text rendering. It also includes haptic feedback for an engaging user experience.

----------

## Features

-   🎡 **Animated Spinning Wheel**: Smooth animations for spinning the wheel.
-   🖌️ **Customizable Segments**: Supports alternating colors, text, and borders.
-   🛠️ **Haptic Feedback**: Provides tactile feedback when the wheel spins.
-   🎨 **Dynamic Design**: Easy to customize segment colors, text, and arrow styles.
-   📚 **Clean Architecture**: Code organized with clear separations for rendering, animations, and utilities.

----------

## Screenshots



https://github.com/user-attachments/assets/e225e08f-be90-4a79-8961-94c02a07b8f7


----------

## Installation

1.  Clone the repository:
    
    bash
    
    Copier le code
    
    `git clone https://github.com/yourusername/spinwheel.git
    cd spinwheel` 
    
2.  Open the project in **Android Studio**.
    
3.  Build and run the app on an emulator or physical device.
    

----------

## Usage

### Setting Up the Spinning Wheel

To use the spinning wheel in your Jetpack Compose screen, call the `SpinningWheel` composable with a list of items 

### Customization

#### Changing Segment Colors and Text

Customize the `drawSegment` and `drawSegmentText` functions in `WheelSegmentRenderer.kt` and `WheelTextRenderer.kt`.

#### Haptic Feedback

Modify the haptic feedback settings in `animateWheelSpin` located in `WheelAnimationUtils.kt`.

----------

## Contributing

Contributions are welcome! If you have suggestions, bug fixes, or new features, feel free to open a pull request.

### Steps to Contribute

1.  Fork the repository.
2.  Create a new branch for your changes.
3.  Submit a pull request with a detailed description of your changes.

----------

## License

This project is licensed under the MIT License. See the LICENSE file for details.

----------

## Acknowledgments

-   [Jetpack Compose Documentation](https://developer.android.com/jetpack/compose)
-   [Haptic Feedback in Compose](https://developer.android.com/jetpack/compose/haptic-feedback)
-   Open-source contributors ❤️

----------

### Future Enhancements

-   Add sound effects during spinning.
-   Support for multi-wheel configurations.
-   Advanced animations for segment highlighting.
