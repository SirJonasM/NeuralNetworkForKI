# NeuralNetworkForKI

WetterAI ist die Abgabe, in ihr befindet sich die Main-Methode.

Sie erstellt ein neuronales Netz mit 2 Hidden Layers, die jeweils 2 Neuronen enthalten.
Layer 1 enthält eine Hyperbolic Tangent Aktivierungsfunktion, Layer 2 eine Sigmoidfunktion.

Das Netz lernt mithilfe eines backpropagation-Algorithmus. Dieser arbeitet zudem mit einem Momentum,
um schneller zu lernen.

Es gibt eine Wetter1.txt und Wetter2.txt Datei, in welchen jeweils Punkte (Muster) gespeichert sind.
Wetter1.txt wird benutzt, um das Netz zu trainieren, Wetter2.txt wird zur Evaluation genutzt.

Nach der Evaluierung wird ein Bild erzeugt, welches die Ergebnisse des neuronalen Netzes veranschaulichen soll.
Im Bild wird durch die Farben Rot und Blau dargestellt, ob das Netz eine 1 oder eine 0
an dem jeweiligen Punkt vorhersagt.
Rot steht hierbei für eine 1, Blau für eine 0.

Die Auflösung ist anpassbar über die Methode fillFunction(width, height).
Das Bild ist meist sehr scharf, wenn ein "gutes" neuronales Netz gelernt wurde (wenn der Test gut ausgefallen ist). 
Andernfalls kann das Bild sehr "unscharf" sein.

Zudem wird dieses Bild mit Einsen und Nullen auch in die Konsole geschrieben.

Zusätzlich wird in einem neuen Fenster das trainierte neuronale Netz angezeigt. Die Farbe
repräsentiert hierbei das Gewicht der Verbindungen. (-1 -> blau; +1 -> rot)

Anpassbar sind die Felder (epochen, learningRate) der Klasse WetterAI. Zudem kann man die Anzahl der hiddenLayers
frei wählen sowie die jeweilige Anzahl von Neuronen pro Layer.

Gruppenmitglieder: Jonas Möwes, Gurwinder Singh, Abdualah Al Rade, Leon Ostgathe