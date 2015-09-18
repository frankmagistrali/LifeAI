
public class WanderingState extends World{
	public void WanderingState(Grazer g, String direction){
		int[][] l=getLand();
		Grazer[] grazers=getGrazers();
		int nGraz=getNumGrazers();
		int nearest = nearestGrazer(g, nGraz, grazers);
		int dist=distanceToNearestGrazer(g,nearest);
		if (g.full<40){
			g.currentState="walkingState";
			g.wandering=null;
			grazeState.GrazerState(g);
		}
		else if(nearest!=-1 && dist>150){
			g.currentState="walkingState";
			g.currentState="walkingState";
			g.wandering=null;
			grazeState.GrazerState(g);
		}
		else if (nearest != -1 && grazers[nearest].currentState=="wanderingState"){
			g.wandering=grazers[nearest].wandering;
			move(g,direction,nGraz,grazers,l);
		}
		else{
		move(g,direction,nGraz,grazers,l);
		}
	}
	int distanceToNearestGrazer(Grazer g,int nearest){
		
		return nearest;
	}
	boolean legalMove(int i, int j,int nGraz,Grazer[] graz,int[][] l){

		if (i>n-1 || i<1 || j>n-1 || j<1 || l[i][j]==-1){
			return false;
		}else{ for(int w=0;w<nGraz;w++){
			if(distance2(i, j, graz[w].iPos, graz[w].jPos)==0 && graz[w].alive){
				return false;
			}
		}
		}
		return true;
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
		else{
			g.wandering=pickADirection();
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


}
