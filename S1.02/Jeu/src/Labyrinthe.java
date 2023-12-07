class Labyrinthe extends Program{

    Question newQuestion(String question, String reponse){ //Implémente une fonction et sa réponse
        Question q = new Question();
        q.question = question;
        q.reponse = reponse;
        return q;
    }
    
    String toString (Question q){ //Affiche l'énoncé de la question
        return "" + q.question;
    }

    int nbLignes(String cheminFichier){
        int i = 1;
        extensions.File file = newFile(cheminFichier);
        String verif = readLine(file);
        while(ready(file)){
            i = i + 1;
            verif = readLine(file);
        }
        return i;
    }

    String readFile(String cheminFichier){ //Lis un fichier le renvoie sous forme de chaine de caractère
        extensions.File file = newFile(cheminFichier);
        int longueur = nbLignes(cheminFichier);
        String res = "";
        for(int i = 0; i < longueur; i ++){
            res += readLine(file) + '\n';
        }
        return res;
    }

    int[] grille() 72 x 19

    void algorithm(){
        print(readFile("ressources/img/Presentation.txt"));
    }
}