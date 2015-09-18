
public class EatingState extends World{

public void EatingState(Grazer g) {
int[][] l=getLand();
	if (l[g.iPos][g.jPos]>15){
		g.full+=7;
		l[g.iPos][g.jPos]-=15;
	}else if(l[g.iPos][g.jPos]<=15 &&l[g.iPos][g.jPos]>0){
	g.full+=l[g.iPos][g.jPos]/2;
	l[g.iPos][g.jPos]=0;
	}
		else{
	
		g.currentState="walkingState";
		grazeState.GrazerState(g);
	}
	setLand(l);
}
}
