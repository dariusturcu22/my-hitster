import 'package:flutter/material.dart';
import 'package:mobile_scanner/mobile_scanner.dart';
import 'package:youtube_player_iframe/youtube_player_iframe.dart';
import 'dart:async';

// --- COLOR PALETTE (Catppuccin Mocha) ---
const Color paletteBackground = Color(0xFF181825);
const Color paletteForeground = Color(0xFFCDD6F4);
const Color palettePrimary = Color(0xFFCBA6F7);
const Color paletteSecondary = Color(0xFF585B70);
const Color paletteSurface = Color(0xFF313244);

void main() => runApp(const HitsterApp());

class HitsterApp extends StatelessWidget {
  const HitsterApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      theme: ThemeData(
        scaffoldBackgroundColor: paletteBackground,
        colorScheme: const ColorScheme.dark(
          primary: palettePrimary,
          secondary: paletteSecondary,
        ),
      ),
      home: const HomeScreen(),
    );
  }
}

// --- SHARED GRADIENT BACKGROUND ---
class HitsterBackground extends StatelessWidget {
  final Widget child;
  const HitsterBackground({super.key, required this.child});

  @override
  Widget build(BuildContext context) {
    return Container(
      decoration: const BoxDecoration(
        gradient: LinearGradient(
          begin: Alignment.topLeft,
          end: Alignment.bottomRight,
          colors: [paletteBackground, Color(0xFF1e1e2e), Color(0xFF11111b)],
        ),
      ),
      child: child,
    );
  }
}

// --- MAIN MENU ---
class HomeScreen extends StatelessWidget {
  const HomeScreen({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: HitsterBackground(
        child: Center(
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              const Text(
                "MY HITSTER",
                style: TextStyle(
                  color: palettePrimary,
                  fontSize: 32,
                  fontWeight: FontWeight.bold,
                  letterSpacing: 4,
                ),
              ),
              const SizedBox(height: 20),
              const Icon(
                Icons.music_note_rounded,
                size: 120,
                color: palettePrimary,
              ),
              const SizedBox(height: 40),
              ElevatedButton(
                style: ElevatedButton.styleFrom(
                  backgroundColor: palettePrimary,
                  foregroundColor: paletteBackground,
                  padding: const EdgeInsets.symmetric(
                    horizontal: 50,
                    vertical: 20,
                  ),
                  shape: RoundedRectangleBorder(
                    borderRadius: BorderRadius.circular(15),
                  ),
                ),
                onPressed: () => Navigator.push(
                  context,
                  MaterialPageRoute(builder: (context) => const GameScreen()),
                ),
                child: const Text(
                  "START SCANNER",
                  style: TextStyle(fontWeight: FontWeight.bold),
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }
}

class GameScreen extends StatefulWidget {
  const GameScreen({super.key});
  @override
  State<GameScreen> createState() => _GameScreenState();
}

class _GameScreenState extends State<GameScreen> {
  String? _scannedVideoId;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      extendBodyBehindAppBar: true,
      appBar: AppBar(
        backgroundColor: Colors.transparent,
        elevation: 0,
        iconTheme: const IconThemeData(color: palettePrimary),
      ),
      body: _scannedVideoId == null
          ? HitsterScannerWidget(
              onCodeScanned: (id) => setState(() => _scannedVideoId = id),
            )
          : HitsterPlayerWidget(
              videoId: _scannedVideoId!,
              onReset: () => setState(() => _scannedVideoId = null),
            ),
    );
  }
}

// --- SCANNER SCREEN ---
class HitsterScannerWidget extends StatelessWidget {
  final Function(String) onCodeScanned;
  const HitsterScannerWidget({super.key, required this.onCodeScanned});

  @override
  Widget build(BuildContext context) {
    return Stack(
      children: [
        MobileScanner(
          onDetect: (capture) {
            final String? code = capture.barcodes.first.rawValue;
            if (code != null) onCodeScanned(code);
          },
        ),
        ColorFiltered(
          colorFilter: ColorFilter.mode(
            Colors.black.withValues(alpha: 0.7),
            BlendMode.srcOut,
          ),
          child: Stack(
            children: [
              Container(
                decoration: const BoxDecoration(color: Colors.transparent),
              ),
              Center(
                child: Container(
                  width: 260,
                  height: 260,
                  decoration: BoxDecoration(
                    color: Colors.black,
                    borderRadius: BorderRadius.circular(30),
                  ),
                ),
              ),
            ],
          ),
        ),
        Center(
          child: Container(
            width: 260,
            height: 260,
            decoration: BoxDecoration(
              border: Border.all(color: palettePrimary, width: 3),
              borderRadius: BorderRadius.circular(30),
            ),
          ),
        ),
      ],
    );
  }
}

// --- PLAYER SCREEN ---
class HitsterPlayerWidget extends StatefulWidget {
  final String videoId;
  final VoidCallback onReset;
  const HitsterPlayerWidget({
    super.key,
    required this.videoId,
    required this.onReset,
  });

  @override
  State<HitsterPlayerWidget> createState() => _HitsterPlayerWidgetState();
}

class _HitsterPlayerWidgetState extends State<HitsterPlayerWidget>
    with SingleTickerProviderStateMixin {
  late YoutubePlayerController _youtubeController;
  late AnimationController _vinylAnimationController;
  bool _isPlaying = false;

  @override
  void initState() {
    super.initState();
    _vinylAnimationController = AnimationController(
      vsync: this,
      duration: const Duration(seconds: 4),
    );

    _youtubeController = YoutubePlayerController.fromVideoId(
      videoId: widget.videoId,
      autoPlay: true,
      params: const YoutubePlayerParams(
        showControls: false,
        showFullscreenButton: false,
        origin: 'https://www.youtube-nocookie.com',
        userAgent:
            'Mozilla/5.0 (Linux; Android 14; SM-S911B) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/122.0.0.0 Mobile Safari/537.36',
      ),
    );

    _youtubeController.listen((event) {
      if (mounted) {
        setState(() => _isPlaying = event.playerState == PlayerState.playing);
        _isPlaying
            ? _vinylAnimationController.repeat()
            : _vinylAnimationController.stop();
      }
    });

    Future.delayed(const Duration(milliseconds: 800), () {
      if (mounted) _youtubeController.playVideo();
    });
  }

  @override
  Widget build(BuildContext context) {
    return HitsterBackground(
      child: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Stack(
              alignment: Alignment.center,
              children: [
                // 1x1 Transparent Player (Required by the package to function)
                SizedBox(
                  width: 1,
                  height: 1,
                  child: YoutubePlayer(controller: _youtubeController),
                ),
                // Spinning Vinyl UI
                RotationTransition(
                  turns: _vinylAnimationController,
                  child: Container(
                    width: 280,
                    height: 280,
                    decoration: BoxDecoration(
                      shape: BoxShape.circle,
                      boxShadow: [
                        BoxShadow(
                          color: Colors.black.withValues(alpha: 0.5),
                          blurRadius: 30,
                        ),
                      ],
                      gradient: const RadialGradient(
                        colors: [
                          Color(0xFF2b2b3b),
                          Colors.black,
                          Color(0xFF11111b),
                        ],
                        stops: [0.0, 0.8, 1.0],
                      ),
                    ),
                    child: CustomPaint(painter: VinylShinePainter()),
                  ),
                ),
                // Play/Pause Button
                GestureDetector(
                  onTap: () => _isPlaying
                      ? _youtubeController.pauseVideo()
                      : _youtubeController.playVideo(),
                  child: Container(
                    width: 80,
                    height: 80,
                    decoration: const BoxDecoration(
                      color: palettePrimary,
                      shape: BoxShape.circle,
                    ),
                    child: Icon(
                      _isPlaying
                          ? Icons.pause_rounded
                          : Icons.play_arrow_rounded,
                      size: 50,
                      color: paletteBackground,
                    ),
                  ),
                ),
              ],
            ),
            const SizedBox(height: 80),
            SizedBox(
              width: 280,
              height: 60,
              child: ElevatedButton.icon(
                icon: const Icon(Icons.qr_code_scanner_rounded),
                label: const Text(
                  "SCAN NEXT",
                  style: TextStyle(fontSize: 18, fontWeight: FontWeight.bold),
                ),
                style: ElevatedButton.styleFrom(
                  backgroundColor: paletteSecondary,
                  foregroundColor: paletteForeground,
                  shape: RoundedRectangleBorder(
                    borderRadius: BorderRadius.circular(30),
                    side: const BorderSide(color: palettePrimary, width: 2),
                  ),
                ),
                onPressed: widget.onReset,
              ),
            ),
          ],
        ),
      ),
    );
  }

  @override
  void dispose() {
    _youtubeController.close();
    _vinylAnimationController.dispose();
    super.dispose();
  }
}

// VINYL SHINE EFFECT
class VinylShinePainter extends CustomPainter {
  @override
  void paint(Canvas canvas, Size size) {
    final center = Offset(size.width / 2, size.height / 2);

    // Draw subtle grooves
    final groovePaint = Paint()
      ..color = Colors.white.withValues(alpha: 0.05)
      ..style = PaintingStyle.stroke
      ..strokeWidth = 1;

    for (var i = 1; i < 10; i++) {
      canvas.drawCircle(center, (size.width / 2.2) - (i * 10), groovePaint);
    }

    final shinePaint = Paint()
      ..shader = RadialGradient(
        colors: [Colors.white.withValues(alpha: 0.05), Colors.transparent],
      ).createShader(Rect.fromLTWH(0, 0, size.width, size.height));

    canvas.drawRect(Rect.fromLTWH(0, 0, size.width, size.height), shinePaint);
  }

  @override
  bool shouldRepaint(CustomPainter oldDelegate) => false;
}
