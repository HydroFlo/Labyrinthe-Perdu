import extensions.CSVFile;

class Labyrinthe extends Program{

    final int NOMBRESALLES = 10;
    
    Salle[] salles = new Salle[]{
                                    newSalle(1,"0101"),
                                    newSalle(2,"1111"),
                                    newSalle(3,"0101"),
                                    newSalle(4,"1010"),
                                    newSalle(5,"1101"),
                                    newSalle(6,"1100"),
                                    newSalle(7,"1001"),
                                    newSalle(8,"1011"),
                                    newSalle(9,"0011"),
                                    newSalle(10,"0110")
                                };
    

    Salle newSalle(int numero, String sorties){
        Salle s = new Salle();
        s.numero=numero;
        s.sorties=sorties;
        return(s);
    }

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

    Joueur newJoueur(String pseudo){ //Créé un nouveau Joueur
        Joueur j = new Joueur();
        j.pseudo = pseudo;
        j.score = 0;
        return j;
    }

    int nbLignes(String cheminFichier){ //retourne le nombre de ligne d'un fichier
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

    String veriferVoisins(Salle[][] lab, int i, int j){
        String resultat = "";
        if (i<length(lab,1) && i>=1 && lab[i-1][j]!=null){
            resultat = resultat + lab[i-1][j].sorties;
        }else{resultat=resultat+"EEEE";}
        if (j<length(lab,2)-1 && lab[i][j+1]!=null){
            resultat = resultat + lab[i][j+1].sorties;
        }else{resultat=resultat+"EEEE";}
        if (i<length(lab,1)-1 && lab[i+1][j]!=null){
            resultat = resultat + lab[i+1][j].sorties;
        }else{resultat=resultat+"EEEE";}
        if (j<length(lab,2)&& j>=1 && lab[i][j-1]!=null){
            resultat = resultat + lab[i][j-1].sorties;
        }else{resultat=resultat+"EEEE";}
        println(resultat);
        return(resultat);
    }

    void choisirSalle(Salle[][] lab, int i, int j){
        String resultat="";
        String check = veriferVoisins(lab,i,j);
        String haut = substring(check,0,4);
        String droite = substring(check,4,8);
        String bas = substring(check,8,12);
        String gauche = substring(check,12,16);
        if(charAt(haut,2)=='1'){
            resultat=resultat+'1';
        }else{resultat=resultat+'.';}
        if(charAt(droite,3)=='1'){
            resultat=resultat+'1';
        }else{resultat=resultat+'.';}
        if(charAt(bas,0)=='1'){
            resultat=resultat+'1';
            }else{resultat=resultat+'.';}
        if(charAt(gauche,1)=='1'){
            resultat=resultat+'1';
        }else{resultat=resultat+'.';}
        println(resultat);
        int nbalea=(int)(random()*10);
        while(!equals(salles[nbalea].sorties,choixAlea(resultat))){
            nbalea=(int)(random()*10);
            println(nbalea);
        }
        lab[i][j]=salles[nbalea];
    }

    String choixAlea(String chaine){
        for(int a = 0; a<4;a++){
            if(charAt(chaine,a)=='.'||charAt(chaine,a)=='E'){
                chaine=substring(chaine,0,a)+(int)(random()*2)+substring(chaine,a+1,length(chaine));
            }
        }
        return(chaine);
    }

    Salle[][] genererLab(int nbSalle){ // genere un Layrinthe de nbSalle salle et d'une taille de 72 x 19 par salle !!!! IL FAUT QUE LE nbSalle SOINT IMPAIRE !!!!
        Salle[][] lab = new Salle[nbSalle][nbSalle];
        lab[(nbSalle/2)+1][(nbSalle/2)+1] = salles[2];
        for(int i=0;i<length(lab,1);i++){
            for(int j=0;j<length(lab,2);j++){
                if(lab[i][j]==null){
                    choisirSalle(lab,i,j);
                }
            }
        }
        return(lab);
    }

    int[] deplacement(char[][] Lab, char direction, int positionL, int positionC, Joueur j){ //vérifie si déplacement possible, si oui l'effectue
        if(direction == 'z' && positionL-1 >= 0 && Lab[positionL-1][positionC] != '@'){ //déplacement haut
            if(Lab[positionL-1][positionC] == '.'){ //déplacement si case vide
                Lab[positionL][positionC] = '.';
                Lab[positionL-1][positionC] = 'P';
                return new int[]{positionL-1, positionC};
            } else if(Lab[positionL-1][positionC] == 'M'){ //Si Monstre, affiche la question.
                Question q = newQuestion("Quelle est la capital de la France", "paris");
                afficheQuestion(q, true);
                if(questionCorrect(q)){ //En cas de bonne réponse efface le monstre
                    Lab[positionL-1][positionC] = '.';
                    j.score += 1;
                } else {
                    j.vie -= 10;
                }
            }
        }

        if(direction == 's' && positionL+1 < length(Lab, 1) && Lab[positionL+1][positionC] != '@'){ //déplacement bas
            if(Lab[positionL+1][positionC] == '.'){ //déplacement si case vide
                Lab[positionL][positionC] = '.';
                Lab[positionL+1][positionC] = 'P';
                return new int[]{positionL+1, positionC};
            } else if (Lab[positionL+1][positionC] == 'M'){
                Question q = newQuestion("Quelle est la capital de la France", "paris");
                afficheQuestion(q, true);
                if(questionCorrect(q)){ //En cas de bonne réponse efface le monstre
                    Lab[positionL+1][positionC] = '.';
                    j.score += 1;
                } else {
                    j.vie -= 10;
                }
            }
        }

        if(direction == 'q' && positionC-1 >= 0 && Lab[positionL][positionC-1] != '@'){ //déplacement gauche
            if(Lab[positionL][positionC-1] == '.'){ //déplacement si case vide
                Lab[positionL][positionC] = '.';
                Lab[positionL][positionC-1] = 'P';
                return new int[]{positionL, positionC-1};
            } else if (Lab[positionL][positionC-1] == 'M'){
                Question q = newQuestion("Quelle est la capital de la France", "paris");
                afficheQuestion(q, true);
                if(questionCorrect(q)){ //En cas de bonne réponse efface le monstre
                    Lab[positionL][positionC-1] = '.';
                    j.score += 1;
                } else {
                    j.vie -= 10;
                }
            }
        }

        if(direction == 'd' && positionC+1 < length(Lab,2) && Lab[positionL][positionC+1] != '@'){ //déplacement droite
            if(Lab[positionL][positionC+1] == '.'){ //déplacement si case vide
                Lab[positionL][positionC] = '.';
                Lab[positionL][positionC+1] = 'P';
                return new int[]{positionL, positionC+1};
            } else if (Lab[positionL][positionC+1] == 'M'){
                Question q = newQuestion("Quelle est la capital de la France", "paris");
                afficheQuestion(q, true);
                if(questionCorrect(q)){ //En cas de bonne réponse efface le monstre
                    Lab[positionL][positionC+1] = '.';
                    j.score += 1;
                } else {
                    j.vie -= 10;
                }
            }
        }
        return new int[]{positionL, positionC};
    }

    String formatIntituler(String intituler, int tailleTotal, int tailleLigne){ //prend une chaine de caractère et la renvoie sous le format donné
        String res = "";
        int format = 0;
        for(int longueur = 0; longueur < tailleTotal; longueur ++){
            if(longueur < length(intituler)){
                res += charAt(intituler, longueur);
            } else {
                res += " ";
            }
            format += 1;
            if(format == tailleLigne && longueur != tailleTotal-1){
                res += '\n';
                format = 0;
            }
        }
        return res;
    }

    void testFormatIntituler(){
        String test = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOP";
        String verif = "abcdefghijkl" +'\n' + "mnopqrstuvwx" + '\n' + "yzABCDEFGHIJ" + '\n' + "KLMNOP      ";
        assertEquals(verif, formatIntituler(test, 48, 12));
    }        

    void afficheIntituler(String intituler){ //prend une chaine et l'affiche selon la forme d'affichage des question (voir ressource)
        print("                ");
        for(int i = 0; i < length(intituler); i ++){
            print(charAt(intituler, i));
            if(charAt(intituler, i) == '\n'){
                print("                ");
            }
        }
        println();
    }

    String[][] load(String cheminFichier){ //Charge un fichier csv en un tableau
        CSVFile file = loadCSV(cheminFichier);
        String tab[][] =  new String[rowCount(file)][columnCount(file)];
        for(int i = 0; i < rowCount(file)-1; i ++){
            for(int j = 0; j < columnCount(file); j++){
                tab[i][j] = getCell(file, i, j);
            }
        }
        return tab;
    }

    void afficheStringTab(String[][] tab){ //Affiche un tableau de String a 2 dimension
        for(int i = 0; i < length(tab,1); i ++){
            for(int j = 0; j < length(tab,2); j ++){
                print(tab[i][j] + "  ");
            }
            println();
        }
    }

    void afficheQuestion(Question q, boolean reponseLibre){
        println("################################################################################" + '\n' +
                "####@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@####" + '\n' +
                "####@                                                                      @####");
        String intituler = formatIntituler(q.question, 48*4, 48);
        afficheIntituler(intituler);
        if(reponseLibre){
            println("####@                                                                      @####" + '\n' +
                    "####@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@####" + '\n' +
                    "################################################################################");
            for(int i = 0; i < 13; i ++){
                println();
            }
        }

    }

    boolean questionCorrect(Question q){ //Vérifie si on donne la bonne réponse
        String res = readString();
        return equals(res, q.reponse);
    }

    void afficheLab(char[][] Lab){ //affiche le Labyrinthe (@ = mur, P = perso, S = sortie, M = monstre, B = boss, 🏠 = shop, .  = case vide)
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
    void afficherSalle(int nbr){
        char[][] salle = genererSalle("ressources/Lab/Salle"+nbr);
        afficheLab(salle);
    }
    void algorithm(){
        Salle[][] lab = genererLab(5); //genere le Layrinthe
        for(int i = 0; i<length(lab,1);i++){
            for(int j=0;j<length(lab,2);j++){
                afficherSalle(lab[i][j].numero);
            }
        }
        print(readFile("ressources/img/Presentation.txt", true));
        String lancer = readString();
        while(lancer != ""){
            print(readFile("ressources/img/Presentation.txt", true));
            lancer = readString();
        }
        print("Rentrez votre pseudo : ");
        String pseudo = readString();
        Joueur j = newJoueur(pseudo);
        println("" + j.pseudo + " / score : " + j.score + " / vie : " + j.vie + " / boss vaincu ? " + j.bossVaincu);
        char[][] salle1 = genererSalle("ressources/Lab/Salle1");
        Question q = newQuestion("Quelle est la capital de la France", "paris");
        int[] indiceM = indiceDe('P', salle1);
        salle1[indiceM[0]-1][indiceM[1]] = 'M';
        afficheStringTab(load("ressources/score.csv"));
        while(j.vie >= 0 && !j.bossVaincu){
            afficheLab(salle1);
            println("" + j.pseudo + " / score : " + j.score + " / vie : " + j.vie + " / boss vaincu ? " + j.bossVaincu);
            int[] indiceP = indiceDe('P', salle1);
            char choix = controleSaisie();
            indiceP = deplacement(salle1, choix, indiceP[0], indiceP[1], j);
        }
        
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
        newQuestion("En quelle année l'Amérique à été découverte par les européens ?","1492");
        newQuestion("Qui à découvert l'Amérique ?","Christophe Colomb");

        
    }
}
