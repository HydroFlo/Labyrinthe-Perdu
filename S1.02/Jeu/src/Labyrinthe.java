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

    String readFile(int longueur, extensions.File file){ //Lis un fichier le renvoie sous forme de chaine de caractère
        String res = "";
        for(int i = 0; i < longueur; i ++){
            res += readLine(file) + '\n';
        }
        return res;
    }

    void algorithm(){
        print(readFile(23, newFile("ressources/img/Presentation.txt")));

    }
}