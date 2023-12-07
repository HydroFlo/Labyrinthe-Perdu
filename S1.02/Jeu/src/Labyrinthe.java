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

    char ControleSaisie(){ // verifie que l'utilisateur saisisse bien 1 caractere
        do{
            String choix = readString();
        }while(length(choix)== 1);
        return charAt(choix, 0);


    }

    char[][] genererLab(int nbSalle){ // genere un Layrinthe de nbSalle salle et d'une taille de 72 x 19 par salle
        return new char[1][1];
    } 

    void deplacement(char[][] Lab, char direction, int positionL, int positionC){ //vérifie si déplacement possible, si oui l'effectue
        if(direction == 'z' && positionL-1 >= 0 && Lab[positionL-1][positionC] != '@'){ //déplacement haut
            Lab[positionL][positionC] = '.';
            if(Lab[positionL-1][positionC] == '.'){ //déplaceent si case vide
                Lab[positionL-1][positionC] = 'P';
            }
        }
        if(direction == 'z' && positionL+1 < length(Lab, 1) && Lab[positionL+1][positionC] != '@'){ //déplacement bas
            Lab[positionL][positionC] = '.';
            if(Lab[positionL+1][positionC] == '.'){ //déplaceent si case vide
                Lab[positionL+1][positionC] = 'P';
            }
        }
        if(direction == 'z' && positionC-1 >= 0 && Lab[positionC-1][positionC] != '@'){ //déplacement gauche
            Lab[positionL][positionC] = '.';
            if(Lab[positionC-1][positionC] == '.'){ //déplaceent si case vide
                Lab[positionC-1][positionC] = 'P';
            }
        }
        if(direction == 'z' && positionC+1 < length(Lab,2) && Lab[positionC+1][positionC] != '@'){ //déplacement droite
            Lab[positionL][positionC] = '.';
            if(Lab[positionC+1][positionC] == '.'){ //déplaceent si case vide
                Lab[positionC+1][positionC] = 'P';
            }
        }
    }

    void afficheLab(char[][] Lab){ //affiche le Labyrinthe (@ = mur, P = perso, E = sortie, M = monstre, B = boss, S = shop, . =c ase vide)

    }

    void algorithm(){
        genererLab(int nbCase) //genere le Layrinthe
        print(readFile("ressources/img/Presentation.txt"));
        while(!jeufini()){
            char choix = ControleSaisie();
            if(choix == 'z' || choix == 'q' || choix == 's' || choix == 'd'){
                deplacement(jeu, choix);
            }
        }
    }
}