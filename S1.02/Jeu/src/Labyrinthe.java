import extensions.CSVFile;

class Labyrinthe extends Program{

    final int NOMBRESALLES = 10;
    
    Salle[] salles = new Salle[]{
                                    newSalle(0,"0110"),
                                    newSalle(1,"0101"),
                                    newSalle(2,"1111"),
                                    newSalle(3,"0101"),
                                    newSalle(4,"1010"),
                                    newSalle(5,"1101"),
                                    newSalle(6,"1100"),
                                    newSalle(7,"1001"),
                                    newSalle(8,"1011"),
                                    newSalle(9,"0011"),
                                    newSalle(10,"0110"),
                                    newSalle(11,"0100"),
                                    newSalle(12,"0001"),
                                    newSalle(13,"0010"),
                                    newSalle(14,"1000"),
                                    newSalle(15,"0000")
                                };
    

    Salle newSalle(int numero, String sorties){ //Création d'une salle
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

    String controleSaisie(){ // verifie que l'utilisateur saisisse bien 1 caractere
        String choix;
        do{
            choix = toLowerCase(readString());
        }while(length(choix) != 1 && length(choix) != 2 );
        return choix;
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
        }else{resultat=resultat+"....";}
        if (j<length(lab,2)-1 && lab[i][j+1]!=null){
            resultat = resultat + lab[i][j+1].sorties;
        }else{resultat=resultat+"....";}
        if (i<length(lab,1)-1 && lab[i+1][j]!=null){
            resultat = resultat + lab[i+1][j].sorties;
        }else{resultat=resultat+"....";}
        if (j<length(lab,2)&& j>=1 && lab[i][j-1]!=null){
            resultat = resultat + lab[i][j-1].sorties;
        }else{resultat=resultat+"....";}
        //println(resultat);
        return(resultat);
    }

    void choisirSalle(Salle[][] lab, int i, int j){
        String resultat="";
        String check = veriferVoisins(lab,i,j);
        String haut = substring(check,0,4);
        String droite = substring(check,4,8);
        String bas = substring(check,8,12);
        String gauche = substring(check,12,16);
        if(i==0){
            haut = "0000";
        }
        if(i==length(lab,1)-1){
            bas = "0000";
        }
        if(j==0){
            gauche = "0000";
        }
        if(j==length(lab,2)-1){
            droite = "0000";
            println("DROITE");
        }
        println(haut);
        println(droite);
        println(bas);
        println(gauche);
        resultat = resultat + charAt(haut,2);
        resultat = resultat + charAt(droite,3);
        resultat = resultat + charAt(bas,0);
        resultat = resultat + charAt(gauche,1);
        // if(charAt(haut,2)=='1'){
        //     resultat=resultat+'1';
        // }else if(charAt(haut,2)=='0'){
        //     resultat=resultat+'0';
        // }else{resultat=resultat+'.';}

        // if(charAt(droite,3)=='1'){
        //     resultat=resultat+'1';
        // }else if(charAt(droite,3)=='0'){
        //     resultat=resultat+'0';
        // }else{resultat=resultat+'.';}

        // if(charAt(bas,0)=='1'){
        //     resultat=resultat+'1';
        // }else if(charAt(bas,0)=='0'){
        //     resultat=resultat+'0';
        // }else{resultat=resultat+'.';}

        // if(charAt(gauche,1)=='1'){
        //     resultat=resultat+'1';
        // }else if(charAt(gauche,1)=='0'){
        //     resultat=resultat+'0';
        // }else{resultat=resultat+'.';}

        println(resultat);


        if(equals(resultat,".000")||equals(resultat,"1000")){
            lab[i][j]=salles[14];
        }else if(equals(resultat,"0.00")||equals(resultat,"0100")){
            lab[i][j]=salles[11];
        }else if(equals(resultat,"00.0")||equals(resultat,"0010")){
            lab[i][j]=salles[13];
        }else if(equals(resultat,"000.")||equals(resultat,"0001")){
            lab[i][j]=salles[12];
        }else if (equals(resultat,"0000")){
            lab[i][j]=salles[15];
        }else{
            int nbalea=(int)(random()*10);
            while(!equals(salles[nbalea].sorties,choixAlea(resultat))){
                print("Salle vérifier : ");
                println(salles[nbalea].sorties);
                nbalea=(int)(random()*10);
                //println(nbalea);
                
            }
            lab[i][j]=salles[nbalea];
        }
        println("FINI");
    }

    String choixAlea(String chaine){
        for(int a = 0; a<4;a++){
            if(charAt(chaine,a)=='.'){
                chaine=substring(chaine,0,a)+(int)(random()*2)+substring(chaine,a+1,length(chaine));
            }
        }
        println(chaine);
        return(chaine);
    }

    Salle[][] genererLab(int nbSalle){ // genere un Layrinthe de nbSalle salle et d'une taille de 72 x 19 par salle !!!! IL FAUT QUE LE nbSalle SOINT IMPAIRE !!!!
        Salle[][] lab = new Salle[nbSalle][nbSalle];
        lab[0][0] = salles[0];
        for(int i=0;i<length(lab,1);i++){
            for(int j=0;j<length(lab,2);j++){
                if(lab[i][j]==null){
                    choisirSalle(lab,i,j);
                }
            }
        }
        return(lab);
    }

    void afficheHelp(){
        print(readFile("ressources/img/Help", true));
        println("A tout moment dans la partie, appuyer sur \"H\" pour réafficher ce menu");
        println();
        readString();
    }

    int[] changeSalle(char[][] Lab, int positionL, int positionC, int[] indiceSalle){
        if(positionC == 1){
            indiceSalle[1] -= 1;
            return new int[]{9, length(Lab, 2)-3};
        }
        if(positionL == 1){
            indiceSalle[0] -= 1;
            return new int[]{length(Lab, 1)-3, length(Lab, 2)/2};
        }
        if(positionC == length(Lab, 2)-2){
            indiceSalle[1] += 1;
            return new int[]{9, 2};
        }
        if(positionL == length(Lab, 1)-2){
            indiceSalle[0] += 1;
            return new int[]{2, length(Lab, 2)/2};
        }
        return new int[]{positionL, positionC};
    }

    int[] deplacement(char[][] Lab, char direction, int positionL, int positionC, Joueur j, Question[] liste, Question[] listeBoss, int[] indiceSalle){ //vérifie si déplacement possible, si oui l'effectue
        if(direction == 'h'){
            afficheHelp();
        }
        
        if(direction == 'z' && positionL-1 >= 0 && Lab[positionL-1][positionC] != '@'){ //déplacement haut
            if(Lab[positionL-1][positionC] == '.'){ //déplacement si case vide
                Lab[positionL][positionC] = '.';
                Lab[positionL-1][positionC] = 'P';
                return new int[]{positionL-1, positionC};
            } else if(Lab[positionL-1][positionC] == 'M'){ //Si Monstre, affiche la question.
                Question q = questionRandom(liste, j.score);
                afficheQuestion(q, true);
                if(questionCorrect(q)){ //En cas de bonne réponse efface le monstre
                    Lab[positionL-1][positionC] = '.';
                    j.score += 1;
                } else {
                    j.vie -= 10;
                }
            }  else if(Lab[positionL-1][positionC] == 'B' && !j.bossVaincu){ //Si Monstre, affiche la question.
                Question q = questionRandom(listeBoss, 0);
                afficheQuestion(q, true);
                if(questionCorrect(q)){ //En cas de bonne réponse efface le monstre
                    j.bossVaincu = true;
                    j.score += 30;
                } else {
                    j.vie -= 10;
                }
            } else if(Lab[positionL-1][positionC] == 'S'){
                Lab[positionL][positionC] = '.';
                return changeSalle(Lab, positionL-1, positionC, indiceSalle);
            }
        }

        if(direction == 's' && positionL+1 < length(Lab, 1) && Lab[positionL+1][positionC] != '@'){ //déplacement bas
            if(Lab[positionL+1][positionC] == '.'){ //déplacement si case vide
                Lab[positionL][positionC] = '.';
                Lab[positionL+1][positionC] = 'P';
                return new int[]{positionL+1, positionC};
            } else if (Lab[positionL+1][positionC] == 'M'){
                Question q = questionRandom(liste, j.score);
                afficheQuestion(q, true);
                if(questionCorrect(q)){ //En cas de bonne réponse efface le monstre
                    Lab[positionL+1][positionC] = '.';
                    j.score += 1;
                } else {
                    j.vie -= 10;
                }
            }  else if(Lab[positionL+1][positionC] == 'B' && !j.bossVaincu){ //Si Monstre, affiche la question.
                Question q = questionRandom(listeBoss, 0);
                afficheQuestion(q, true);
                if(questionCorrect(q)){ //En cas de bonne réponse efface le monstre
                    j.bossVaincu = true;
                    j.score += 30;
                } else {
                    j.vie -= 10;
                }
            } else if (Lab[positionL+1][positionC] == 'S'){
                Lab[positionL][positionC] = '.';
                return changeSalle(Lab, positionL+1, positionC, indiceSalle);
            }
        }

        if(direction == 'q' && positionC-1 >= 0 && Lab[positionL][positionC-1] != '@'){ //déplacement gauche
            if(Lab[positionL][positionC-1] == '.'){ //déplacement si case vide
                Lab[positionL][positionC] = '.';
                Lab[positionL][positionC-1] = 'P';
                return new int[]{positionL, positionC-1};
            } else if (Lab[positionL][positionC-1] == 'M'){
                Question q = questionRandom(liste, j.score);
                afficheQuestion(q, true);
                if(questionCorrect(q)){ //En cas de bonne réponse efface le monstre
                    Lab[positionL][positionC-1] = '.';
                    j.score += 1;
                } else {
                    j.vie -= 10;
                }
            }  else if(Lab[positionL][positionC-1] == 'B' && !j.bossVaincu){ //Si Monstre, affiche la question.
                Question q = questionRandom(listeBoss, 0);
                afficheQuestion(q, true);
                if(questionCorrect(q)){ //En cas de bonne réponse efface le monstre
                    j.bossVaincu = true;
                    j.score += 30;
                } else {
                    j.vie -= 10;
                }
            }  else if (Lab[positionL][positionC-1] == 'S'){
                Lab[positionL][positionC] = '.';
                return changeSalle(Lab, positionL, positionC-1, indiceSalle);
            }
        }

        if(direction == 'd' && positionC+1 < length(Lab,2) && Lab[positionL][positionC+1] != '@'){ //déplacement droite
            if(Lab[positionL][positionC+1] == '.'){ //déplacement si case vide
                Lab[positionL][positionC] = '.';
                Lab[positionL][positionC+1] = 'P';
                return new int[]{positionL, positionC+1};
            } else if (Lab[positionL][positionC+1] == 'M'){
                Question q = questionRandom(liste, j.score);
                afficheQuestion(q, true);
                if(questionCorrect(q)){ //En cas de bonne réponse efface le monstre
                    Lab[positionL][positionC+1] = '.';
                    j.score += 1;
                } else {
                    j.vie -= 10;
                }
            }  else if(Lab[positionL][positionC+1] == 'B' && !j.bossVaincu){ //Si Monstre, affiche la question.
                Question q = questionRandom(listeBoss, 0);
                afficheQuestion(q, true);
                if(questionCorrect(q)){ //En cas de bonne réponse efface le monstre
                    j.bossVaincu = true;
                    j.score += 30;
                } else {
                    j.vie -= 10;
                }
            }  else if (Lab[positionL][positionC+1] == 'S'){
                Lab[positionL][positionC] = '.';
                return changeSalle(Lab, positionL, positionC+1, indiceSalle);

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

    void afficheIntituler(String intituler){ //prend une chaine et l'affiche selon la forme d'affichage des question (voir ressources)
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
        for(int i = 0; i < rowCount(file); i ++){
            for(int j = 0; j < columnCount(file); j++){
                tab[i][j] = getCell(file, i, j);
            }
        }
        return tab;
    }

    void ajoutQuestion(String[][] file, int nbAjout){ //Permet d'ajouter nbAjout nouvelle Question
        String[][] newFile = new String[length(file, 1) + nbAjout][length(file, 2)];
        for(int i1 = 0; i1 < length(file, 1); i1 ++){ //copie le fichier de base
            for(int j1 = 0; j1 < length(file, 2); j1 ++){
                newFile[i1][j1] = file[i1][j1];
            }
        }
        for(int i = length(file, 1); i < length(newFile); i ++){ //ajoute autant de question que demandé à l'appelle de la fonction
            print("Quel est l'intitulé de la nouvelle question ? : ");
            String intitu = readString();
            print("Quelle est la réponse à cette question ? : ");
            String rep = readString();
            newFile[i][0]= intitu;
            newFile[i][1]= rep;
        }
        saveCSV(newFile, "ressources/ListeQuestion.csv");

    }

    void ajoutScore(String[][] file, String pseudo, int score){ //ajout de Score (optionnel à faire plus tard)
        if(score > stringToInt(file[length(file,1)-1][1])){
            int i = 1; //permet de compter d'ajouter les fichier de file dans newFile après ajout de ligne
            String[][] newFile = new String[length(file, 1)][length(file, 2)];
            boolean fait = false;
            newFile[0][0] = file[0][0];
            newFile[0][1] = file[0][1];
            for(int i1 = 1; i1 < length(newFile, 1); i1 ++){ //copie le fichier de base
                if(score >= stringToInt(file[i][1]) && !fait){ //ajoute le score du joueur a la place qu'il mérite (en cas d'égalité remplace l'ancien ;) )
                    newFile[i1][0] = pseudo;
                    newFile[i1][1] = "" + score;
                    fait = true;
                } else{ //sinon remet les ancienne ligne
                    for(int j1 = 0; j1 < length(file, 2); j1 ++){
                        newFile[i1][j1] = file[i][j1];
                    }
                    i = i + 1; //incrémente i pour savoir quelle ligne de l'ancien fichier ont été mise dans le nouveau
                }

            }
            saveCSV(newFile, "ressources/score.csv");
        }
    }

    void afficheStringTab(String[][] tab){ //Affiche un tableau de String a 2 dimension
        for(int i = 1; i < length(tab,1); i ++){
            for(int j = 0; j < length(tab,2); j ++){
                print(tab[i][j] + "  ");
            }
            println();
        }
    }

    void afficheQuestion(Question q, boolean reponseLibre){ //Affiche les questions
        print(readFile("ressources/img/entete.txt", true));
        String intituler = formatIntituler(q.question, 48*4, 48);
        afficheIntituler(intituler);
        if(reponseLibre){
            print(readFile("ressources/img/fermeEntete.txt", true));
            for(int i = 0; i < 13; i ++){
                println();
            }
        }

    }

    void afficheScore(){
        String[][] file = load("ressources/score.csv");
        print(readFile("ressources/img/Score.txt", true));
        for(int i = 1; i < length(file,1); i ++){
            String vide = "";
            for(int k = 0; k < 58-length(file[i][0]); k ++){
                vide += " ";
            }
            println("####     " + file[i][0] + vide + file[i][1] + "       ####");
        }
        println(readFile("ressources/img/fermeEntete.txt", true));
    }

    Question[] listeQuestion(String cheminFichier){ //Charge le csv des question et renvoie un tableau de question qui possède une Question par case
        String[][] lQuestion = load(cheminFichier);
        Question[] res = new Question[length(lQuestion, 1)-1];
        for(int i = 0; i < length(res); i ++){
            res[i] = newQuestion(lQuestion[i+1][0], lQuestion[i+1][1]);
        }
        return res;
    }

    void testListeQuestion(){
        Question test = newQuestion("Quelle est la capitale de la France","paris");
        Question test2 = newQuestion("Convertir 10 cm en m.","0,1");
        Question[] tabTest = listeQuestion("ressources/ListeQuestion.csv");
        assertEquals(test.question, tabTest[0].question);
        assertEquals(test2.question, tabTest[length(tabTest)-1].question);
    }

    Question questionRandom(Question[] liste, int nbUtilisees){
        int choix = (int) (random()*(length(liste)-nbUtilisees));
        Question res = liste[choix + nbUtilisees];
        Question temp = liste[nbUtilisees];
        liste[choix] = temp;
        liste[nbUtilisees] = res;
        return res;
    }

    boolean questionCorrect(Question q){ //Vérifie si on donne la bonne réponse
        String res = readString();
        return equals(toLowerCase(res), toLowerCase(q.reponse));
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

    int[] copy(int[] tab){ //copie un tableau d'entier
        int[] res = new int[length(tab)];
        for(int i = 0; i < length(res); i ++){
            res[i] = tab[i];
        }
        return res;
    }

    void testCopy(){
        int[] verif = new int[] {2,6,4,8};
        int[] res = copy(verif);
        assertArrayEquals(verif, res);
    }

    boolean equals(int[] tab1, int[] tab2){ //vérifie l'égalité entre 2 tableau d'entier
        if(length(tab1) == length(tab2)){
            for(int i = 0; i < length(tab1); i ++){
                if(tab1[i] != tab2[i]){
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    void testEqualsTabInt(){
        int[] tab1 = new int[]{1,2,3};
        int[] tab2 = new int[]{1,2,3};
        int[] tab3 = new int[]{1,2,5};
        int[] tab4 = new int[0];
        assertTrue(equals(tab1, tab2));
        assertFalse(equals(tab1, tab3));
        assertFalse(equals(tab1, tab4));
    }

    boolean estInt(String verif){
        if(length(verif) == 0){
            return false;
        }
        for(int i = 0; i < length(verif); i ++){
            if(charAt(verif, i) < '0' || '9' < charAt(verif, i)){
                return false;
            }
        }
        return true;
    }

    void testEstInt(){
        String mot1 = "0t6";
        String mot2 = "54";
        String mot3 = "Lolilol";
        String mot4 = "";
        assertFalse(estInt(mot1));
        assertTrue(estInt(mot2));
        assertFalse(estInt(mot3));
        assertFalse(estInt(mot4));
    }

    int nbFromString(int minimum){ //Demande un nombre à l'utilisateur tant qu'il rentre autre chose qu'un nombre ou un nombre inférieur à la limite
        int nbAjout = 0;
        while(nbAjout < minimum){
            String nb = readString();
            while(!estInt(nb)){
                print("Veuillez entrer un nombre valide : ");
                nb = readString();
            }
            nbAjout = stringToInt(nb);
            if(nbAjout < minimum ){
                print("Veuillez choisir un nombre supérieur ou égale à "+ minimum +" : ");
            }
            
        }
        return nbAjout;
    }

    void algorithm(){
        println("Quel taille voulez vous pour le labyrinthe (la taille sera en : N x N ) ?");
        int tailleLab = nbFromString(3);
        Salle[][] lab = genererLab(tailleLab); //genere le Layrinthe
        String[][] questionTemp = load("ressources/ListeQuestion.csv");
        String[][] tabScore = load("ressources/score.csv");
        print("Voulez vous ajouter des question ? oui (o), non (autre) : ");
        boolean ques = equals(toLowerCase(readString()), "o");
        if(ques){
            print("Combien voulez vous en ajouter ? : ");
            int nbAjout = nbFromString(1);
            ajoutQuestion(questionTemp, nbAjout);
            questionTemp = load("ressources/ListeQuestion.csv");
            afficheStringTab(questionTemp);
        }
        Question[] lQuestion = listeQuestion("ressources/ListeQuestion.csv");
        Question[] lQuestionBoss = listeQuestion("ressources/ListeQuestionBoss.csv");

        for(int i = 0; i<length(lab,1);i++){
            for(int j=0;j<length(lab,2);j++){
                afficherSalle(lab[i][j].numero);
            }
        }

        print(readFile("ressources/img/Presentation.txt", true)); //affiche l'écran titre
        String lancer = readString();
        while(!equals(lancer, "")){ //Vérifie que l'utilisateur fasse "Entrée" et si oui lance le jeu
            print(readFile("ressources/img/Presentation.txt", true));
            lancer = readString();
        }

        afficheHelp();

        print("Rentrez votre pseudo : ");
        String pseudo = readString();
        Joueur joueur = newJoueur(pseudo); //Création du joueur
        int[] indiceSalle = new int[]{0,0};
        char[][] salle = genererSalle("ressources/Lab/Salle"+lab[indiceSalle[0]][indiceSalle[1]].numero);
        int rando = (int) (random() * 3)+1;
        afficherSalle(lab[1][0].numero);
        salle[length(salle,1)/2][length(salle,2)/2] = 'P';
        afficheStringTab(load("ressources/score.csv"));
        afficheLab(salle);
        
        while(joueur.vie > 0 && !joueur.bossVaincu){
            int[] indiceSalleActu = copy(indiceSalle);
            afficheLab(salle);
            println("" + joueur.pseudo + " / Score : " + joueur.score + " / PV : " + joueur.vie );
            int[] indiceP = indiceDe('P', salle);
            String choix = controleSaisie();
            int nbMove = 1;
            if(length(choix) == 2 && estInt(substring(choix,1,2))){
                nbMove = charToInt(charAt(choix,1));
            }
            for(int i = 0; i < nbMove; i ++){
                indiceP = deplacement(salle, charAt(choix,0), indiceP[0], indiceP[1], joueur, lQuestion, lQuestionBoss, indiceSalle);
            }
            if(!equals(indiceSalleActu, indiceSalle)){
                salle = genererSalle("ressources/Lab/Salle"+lab[indiceSalle[0]][indiceSalle[1]].numero);
                salle[indiceP[0]][indiceP[1]] = 'P';
                if(indiceSalle[0] == length(lab)/2 && indiceSalle[1] == length(lab)/2){
                    salle[length(salle, 1)/2][length(salle,2)/2] = 'B';
                } else {
                    if (rando == 1){
                        salle[length(salle, 1)/2][length(salle,2)/2] = 'M';
                    }
                    rando = (int) (random() * 3) + 1;
                }
            }
        }
        if(joueur.bossVaincu){
            print(readFile("ressources/img/Win.txt", true));
        } else {
            print(readFile("ressources/img/Lose.txt", true));
        }  
        ajoutScore(tabScore, joueur.pseudo, joueur.score);
        if(equals(toLowerCase(readString()), "s")){
            afficheScore();
        }
    }
}