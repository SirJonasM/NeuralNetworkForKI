# NeuralNetworkForKI

WetterAI ist die Abgabe,

Sie erstellt ein Neuronales Netz (NN) mit 2 Hidden Layers die jeweis 2 Neuronen enthalten,
Das erste Layer enthält eine Hyperbolic Tangent Aktivierungsfunktion das zweite Layer eine Sigmoidfunktion.

Das Netz lernt mithilfe eines backpropagation ALgorithmuses. Dieser enthält zudem ein Momentum, damit es schneller lernt.

Es gibt eine Wetter1.txt und Wetter2.txt Datei die jeweils Punkte (Muster) enthalten.
Wetter1.txt wird zum trainieren benutzt, Wetter2.txt zum evaluieren

Nach der Evaluierung wird ein Bild erzeugt, welche die Ergebnisse des NN veranschaulicht dabei steht ein rötliches Feld dafür, dass das Netz an diesem Punkt
eine 1 vorhergesagt hätte und ein blaues Feld eine 0. Die Auflösung ist anpassbar über fillFunction(width, hight).
Das Bild ist meist sehr scharf wenn ein "gutes" NN gelernt wurde, also z.B. wenn der Test gut ausgefallen ist.
Ist der Test schlecht ausgefallen ist das Bild sehr "unscharf".

Zudem wird dieses Bild mit Einsen und Nullen auch die Konsole geschrieben.

Am Ende wird noch eine Fenster geöffnet, welches das Netz anzeigt dieses ist eigentlich selbsterklärend.

Man kann die Felder (epochen, learningRate) der Klasse WetterAI anpassen und auch die Anzahl der Hidden Layers und wie viele Neuronen diese beinhalten sollen.




