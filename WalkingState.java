


public class WalkingState extends World{
	public void WalkingState(Grazer g){
		int[][] l=getLand();
		Grazer[] grazers=getGrazers();
		int nGraz=getNumGrazers();
		int nearest=nearestGrazer(g,nGraz,grazers);

		 if (nearest!=-1 && (g.full>=40&& grazers[nearest].full>=40)){ 
			 if(g.sex+grazers[nearest].sex==1){
			 if(g.breedTime==0 && grazers[nearest].breedTime==0){
				if (legalMove(g.iPos+1, g.jPos,nGraz,grazers,l)){
				newGrazer(g.iPos+1, g.jPos);
				g.full-=20;
				grazers[nearest].full-=20;
				if(g.sex==0){
				g.breedTime=30;
				grazers[nearest].breedTime=10;
				}
				else{
					g.breedTime=10;
					grazers[nearest].breedTime=30;
				}
			}
			 }
			 }
		}
		 else if(nearest!=-1 && g.full>30){
			move(g,directionOfNearestGrazer(g,nearestGrazer(g,nGraz,grazers),grazers),nGraz,grazers,l);
			//System.out.println("following");
		}
		 else if (l[g.iPos][g.jPos]>0 && g.full<70){
			g.currentState="eatingState";
			grazeState.GrazerState(g);
		}
		else if (g.full<50){

			move(g,directionOfNearestGrass(g,l),nGraz,grazers,l);
		}
		else{
			g.currentState="wanderingState";
			g.wandering=pickADirection();
			grazeState.GrazerState(g);
		}

	}
	int nearestGrazer(Grazer g,int nGraz,Grazer[] graz){
		

		int closest=-1;
	for (int i = 0; i < nGraz; i++) {
	int	d=(int) distance2(g.iPos, g.jPos, graz[i].iPos, graz[i].jPos);
		if (d>50 && d<200){

			closest=i;
	}	
	}
return closest;
	}
	String directionOfNearestGrazer(Grazer g,int closest,Grazer[] graz){

			int iDir;
			int jDir;
			iDir=graz[closest].iPos-g.iPos;
			jDir=graz[closest].jPos-g.jPos;
			if(jDir==0){
				if(iDir>0){
					return "e";
				}else{
					return "w";
				}
			}else if(iDir==0){
			//	System.out.println(iDir+" "+jDir+" "+closesti+" "+closestj+" "+g.iPos+" "+g.jPos);
				if (jDir>0){
					return "s";
				}else{
					return "n";
				}
			}else if(iDir>0){
				if(jDir>0){
					return "se";
				}else{
					return "ne";
				}
			}else if(jDir>0){
				return "sw";
			}else{
				return "nw";
			}
		
		//return "none";
		}
	
	String directionOfNearestGrass(Grazer g,int[][] l){
		int closesti=-1;
		int closestj=-1;
		int dist=1001;
		for (int i = 0; i < n-1; i++) {
			for (int j = 0; j < n-1; j++) {
				int d=(int)distance2(i, j, g.iPos, g.jPos);
				
					//TODO: This will cause biase towards the top left, make it random if distances are equal
					if (l[i][j]>0 && d<dist){
						closesti=i;
						closestj=j;
						dist=d;

					}
				
			}
		}
		if(dist<1001){
			int iDir;
			int jDir;
			iDir=closesti-g.iPos;
			jDir=closestj-g.jPos;
			if(jDir==0){
				if(iDir>0){
					return "e";
				}else{
					return "w";
				}
			}else if(iDir==0){
			//	System.out.println(iDir+" "+jDir+" "+closesti+" "+closestj+" "+g.iPos+" "+g.jPos);
				if (jDir>0){
					return "s";
				}else{
					return "n";
				}
			}else if(iDir>0){
				if(jDir>0){
					return "se";
				}else{
					return "ne";
				}
			}else if(jDir>0){
				return "sw";
			}else{
				return "nw";
			}
		}
		return null;
	}
	void randomMove(Grazer g,int nGraz,Grazer[] grazers,int[][] l){
		String dir =pickADirection();
		move(g,dir,nGraz,grazers,l);
	}
	void move(Grazer g,String dir,int nGraz,Grazer[] grazers,int[][] l){
		
		if (dir=="n" && legalMove(g.iPos,g.jPos-1,nGraz,grazers,l)){
			g.jPos--;
		}
		else if (dir=="ne" && legalMove(g.iPos+1, g.jPos-1,nGraz,grazers,l)){
			g.jPos--; g.iPos++;
		}
		else if (dir=="e" && legalMove(g.iPos+1, g.jPos,nGraz,grazers,l)){
			g.iPos++;
		}
		else if (dir=="se" && legalMove(g.iPos+1, g.jPos+1,nGraz,grazers,l)){
			g.jPos++; g.iPos++;
		}
		else if (dir=="s" && legalMove(g.iPos, g.jPos+1,nGraz,grazers,l)){
			g.jPos++;
		}
		else if (dir=="sw" && legalMove(g.iPos-1, g.jPos+1,nGraz,grazers,l)){
			g.jPos++; g.iPos--;
		}
		else if (dir=="w" && legalMove(g.iPos-1, g.jPos,nGraz,grazers,l)){
			g.iPos--;
		}
		else if (dir=="nw" && legalMove(g.iPos-1, g.jPos-1,nGraz,grazers,l)){
			g.iPos--; g.jPos--;
		}
		else if(dir==null){
			
		}
		else{
			dir=findLegalMove(g, nGraz, grazers, l);
			move(g,dir,nGraz,grazers,l);
		}
	}
	boolean legalMove(int i, int j,int nGraz,Grazer[] grazers,int[][] l){

		if (i>n-1 || i<0 || j>n-1 || j<0 || l[i][j]==-1){
			return false;
		}else{ for(int w=0;w<nGraz;w++){
			if(distance2(i, j, grazers[w].iPos, grazers[w].jPos)==0 && grazers[w].alive){
				return false;
			}
		}
		}
		return true;
		}
	String findLegalMove(Grazer g,int nGraz,Grazer[] grazers,int[][] l){
		int tries=0;
		while (tries<15){
			String dir=pickADirection();
			if(legalDirection(dir,g,nGraz,grazers,l)){
				return dir;
			}
			tries++;
		}
		return null;
	}
	boolean legalDirection(String dir,Grazer g,int nGraz,Grazer[] grazers,int[][] l){
		if (dir=="n" && legalMove(g.iPos,g.jPos-1,nGraz,grazers,l)){
			return true;
		}
		else if (dir=="ne" && legalMove(g.iPos+1, g.jPos-1,nGraz,grazers,l)){
			return true;
		}
		else if (dir=="e" && legalMove(g.iPos+1, g.jPos,nGraz,grazers,l)){
			return true;
		}
		else if (dir=="se" && legalMove(g.iPos+1, g.jPos+1,nGraz,grazers,l)){
			return true;
		}
		else if (dir=="s" && legalMove(g.iPos, g.jPos+1,nGraz,grazers,l)){
			return true;
		}
		else if (dir=="sw" && legalMove(g.iPos-1, g.jPos+1,nGraz,grazers,l)){
			return true;
		}
		else if (dir=="w" && legalMove(g.iPos-1, g.jPos,nGraz,grazers,l)){
			return true;
		}
		else if (dir=="nw" && legalMove(g.iPos-1, g.jPos-1,nGraz,grazers,l)){
			return true;
		}
		else {
			return false;
		}
	}

}
