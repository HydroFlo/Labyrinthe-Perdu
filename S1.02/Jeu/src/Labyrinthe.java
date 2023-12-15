class Labyrinthe extends Program{

    Question newQuestion(String question, String reponse){ //Implémente une fonction et sa réponse
        Question q = new Question();
        q.question = question;
        q.reponse = reponse;
        return q;
    }
    
    String QuestiontoString (Question q){ //Affiche l'énoncé de la question
        return "" + q.question;
    }
    String ReponsetoString (Question q){ //Affiche la réponse de la question
        return "" + q.reponse;
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

    String readFile(String cheminFichier, boolean sertAffichage){ //Lis un fichier le renvoie sous forme de chaine de caractère
        extensions.File file = newFile(cheminFichier);
        int longueur = nbLignes(cheminFichier);
        String res = "";
        if(sertAffichage){
            for(int i = 0; i < longueur; i ++){
                res += readLine(file) + '\n';
            }
        } else {
            for(int i = 0; i < longueur; i ++){
                res += readLine(file);
            }
        }
        return res;
    }

    char controleSaisie(){ // verifie que l'utilisateur saisisse bien 1 caractere
        String choix;
        do{
            choix = readString();
        }while(length(choix)!= 1);
        return charAt(choix, 0);
    }

    char[][] genererSalle(String cheminFichier){ // génère une grille d'une du labyrinthe d'après un fichier (taille 72x19)
        char[][] lab = new char[19][72];
        String salle = readFile(cheminFichier, false);
        int z = 0;
        for(int i = 0; i < nbLignes(cheminFichier); i ++){
            for(int j = 0; j < length(lab, 2); j ++){
                lab[i][j] = charAt(salle, z);
                z = z + 1;
            }
        }
        return lab;
    }

    /*char[][] genererLab(int nbSalle){ // genere un Layrinthe de nbSalle salle et d'une taille de 72 x 19 par salle
        char[][]new char[nbSalle][nbSalle]{
            
        }
        
        return(tab);
    } */

    int[] deplacement(char[][] Lab, char direction, int positionL, int positionC){ //vérifie si déplacement possible, si oui l'effectue
        if(direction == 'z' && positionL-1 >= 0 && Lab[positionL-1][positionC] != '@'){ //déplacement haut
            Lab[positionL][positionC] = '.';
            if(Lab[positionL-1][positionC] == '.'){ //déplacement si case vide
                Lab[positionL-1][positionC] = 'P';
                return new int[]{positionL-1, positionC};
            }
        }
        if(direction == 's' && positionL+1 < length(Lab, 1) && Lab[positionL+1][positionC] != '@'){ //déplacement bas
            Lab[positionL][positionC] = '.';
            if(Lab[positionL+1][positionC] == '.'){ //déplacement si case vide
                Lab[positionL+1][positionC] = 'P';
                return new int[]{positionL+1, positionC};
            }
        }
        if(direction == 'q' && positionC-1 >= 0 && Lab[positionL][positionC-1] != '@'){ //déplacement gauche
            Lab[positionL][positionC] = '.';
            if(Lab[positionL][positionC-1] == '.'){ //déplacement si case vide
                Lab[positionL][positionC-1] = 'P';
                return new int[]{positionL, positionC-1};
            }
        }
        if(direction == 'd' && positionC+1 < length(Lab,2) && Lab[positionL][positionC+1] != '@'){ //déplacement droite
            Lab[positionL][positionC] = '.';
            if(Lab[positionL][positionC+1] == '.'){ //déplacement si case vide
                Lab[positionL][positionC+1] = 'P';
                return new int[]{positionL, positionC+1};
            }
        }
        return new int[]{positionL, positionC};
    }

    void afficheLab(char[][] Lab){ //affiche le Labyrinthe (@ = mur, P = perso, E = sortie, M = monstre, B = boss, S = shop, .  = case vide)
        println("################################################################################"+ '\n' +
                "################################################################################");
        for(int i =0; i<length(Lab,1);i++){
            print("####");
            for(int j =0; j<length(Lab,2);j++){
                if(Lab[i][j]=='.'){
                    print(' ');
                }else{
                    print(Lab[i][j]);
                }
            }
            print("####");
            println();
        }
        println("################################################################################");
    }

    int[] indiceDe(char c, char[][] tab){ //revoie les indices d'un caractère dans un tableau de caractère
        for(int i = 0; i < length(tab, 1); i ++){
            for(int j = 0; j < length(tab, 2); j ++){
                if(tab[i][j] == c){
                    return new int[]{i,j};
                }
            }
        }
        return new int[]{0,0};
    }

    void algorithm(){
        //genererLab(5); //genere le Layrinthe
        print(readFile("ressources/img/Presentation.txt", true));
        char[][] salle1 = genererSalle("ressources/Lab/Salle1");
        afficheLab(salle1);
        int[] indiceP = indiceDe('P', salle1);
        afficheLab(salle1);
        char choix = controleSaisie();
        indiceP = deplacement(salle1, choix, indiceP[0], indiceP[1]);
        afficheLab(salle1);
        choix = controleSaisie();
        indiceP = deplacement(salle1, choix, indiceP[0], indiceP[1]);
        afficheLab(salle1);
        choix = controleSaisie();
        indiceP = deplacement(salle1, choix, indiceP[0], indiceP[1]);
        afficheLab(salle1);
        choix = controleSaisie();
        indiceP = deplacement(salle1, choix, indiceP[0], indiceP[1]);
        afficheLab(salle1);
        choix = controleSaisie();
        indiceP = deplacement(salle1, choix, indiceP[0], indiceP[1]);
        afficheLab(salle1);
        
        // while(!jeufini()){
        //     char choix = ControleSaisie();
        //     if(choix == 'z' || choix == 'q' || choix == 's' || choix == 'd'){
        //         deplacement(jeu, choix);
        //     }
        // }

        newQuestion("Quelle est la capital de la France", "paris");
        newQuestion("Quel fleuve passe par Paris ?", "seine");
        newQuestion("Qui est Guillaume Apollinaire ?", "poète");
        // newQuestion("Quelle est la raison pour laquelle la Préhistoire a pris fin ?","l'écriture");
        newQuestion("Quelle est la capitale du Danemark ?","Copenhague");
        newQuestion("Combien y a-t-il de fautes dans cette phrase : \"Si tu réssidive, tu auras une sanktion ?\"","2");
        newQuestion("Combien font 5 + 9","14");
        newQuestion("\"On\" ou \"Ont\" : Ils ... un livre neuf.","ont");
        newQuestion("\"On\" ou \"Ont\" : ... adore notre chat.","on");
        newQuestion("\"On\" ou \"Ont\" : Ils ... préparé des surprises.","ont");
        newQuestion("\"On\" ou \"Ont\" : ... arrive bientôt ?","on");
        newQuestion("Ecrire en chiffre : trois-cent-vight-deux","322");
        newQuestion("Ecrire en chiffre : six-cent-six","606");
        newQuestion("Ecrire en chiffre : six-cent-soixante-dix-neuf","679");
        newQuestion("Ecrire en chiffre : soixante-neuf","69");
        newQuestion("Ecrire en chiffre : quatre-cent-quatre-vingts","420");
        newQuestion("Ecrire en lettre : 491","quatre-cent-quatre-vingt-onze");
        newQuestion("Ecrire en lettre : 8","huit");
        newQuestion("Ecrire en lettre : 99","quatre-vingt-dix-neuf");
        newQuestion("A quel temps est conjugé cette phrase : \"Tu comprendras plus tard.\"","futur");
        newQuestion("A quel temps est conjugé cette phrase : \"Je vais manger chez un ami.\"","présent");
        newQuestion("A quel temps est conjugé cette phrase : \"Qu'as tu fais ?\"","passé composé");
        newQuestion("A quel temps est conjugé cette phrase : \"Ca été ?\"","passé");
        newQuestion("Convertir 1548 dg en mg.","154800");
        newQuestion("Convertir 10599 hg en cg.","105990000");
        newQuestion("Convertir 10 cm en m.","0,1");
        newQuestion("En quelle année l'Amérique à été découverte ?","1492");
        newQuestion("Qui à découvert l'Amérique ?","Christophe Colomb");

        
    }
}
