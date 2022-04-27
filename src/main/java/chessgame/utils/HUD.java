package chessgame.utils;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class HUD {
	static int health;
	static boolean ability;
	int time;
	int score;
	static playerForm currentForm;
	static Stage stage;
	static Image currentIcon;
	static Image pawnIcon;
	static Image rookIcon;
	static Image knightIcon;
	static Image bishopIcon;
	static Image emptyheartIcon1;
	static Image emptyheartIcon2;
	static Image emptyheartIcon3;
	static Image heartIcon1;
	static Image heartIcon2;
	static Image heartIcon3;
	static Image abilityCrystal;
	static Image emptyAbilityCrystal;
	
	public HUD(Stage stage) {
		HUD.stage = stage;
		Image bg = UI.image(new Vector2(6.93f,2.1f), new Vector2(.5f, 13.8f), "assets/hud/hudbg.png");
		pawnIcon = UI.image(new Vector2(1.8f,1.8f), new Vector2(.7f, 14f), "assets/hud/cureHorse.png"); 
		rookIcon = UI.image(new Vector2(1.8f,1.8f), new Vector2(.7f, 14f), "assets/hud/cureHorse.png"); 
		knightIcon = UI.image(new Vector2(1.8f,1.8f), new Vector2(.7f, 14f), "assets/hud/horseIcon.png");
		bishopIcon = UI.image(new Vector2(1.8f,1.8f), new Vector2(.7f, 14f), "assets/hud/cureHorse.png");
		currentIcon = pawnIcon;
		
		emptyheartIcon1 = UI.image(new Vector2(1.3f,1.3f), new Vector2(2.8f, 14.23f), "assets/hud/emptyPawnHeart.png");
		emptyheartIcon2 = UI.image(new Vector2(1.3f,1.3f), new Vector2(3.8f, 14.23f), "assets/hud/emptyPawnHeart.png");
		emptyheartIcon3 = UI.image(new Vector2(1.3f,1.3f), new Vector2(4.8f, 14.23f), "assets/hud/emptyPawnHeart.png");
		
		heartIcon1 = UI.image(new Vector2(1.3f,1.3f), new Vector2(2.8f, 14.23f), "assets/hud/pawnHeart.png");
		heartIcon2 = UI.image(new Vector2(1.3f,1.3f), new Vector2(3.8f, 14.23f), "assets/hud/pawnHeart.png");
		heartIcon3 = UI.image(new Vector2(1.3f,1.3f), new Vector2(4.8f, 14.23f), "assets/hud/pawnHeart.png");
		
		abilityCrystal = UI.image(new Vector2(1.3f,1.3f), new Vector2(6.205f, 14.23f), "assets/hud/AbilityGem2.png");
		emptyAbilityCrystal = UI.image(new Vector2(1.3f,1.3f), new Vector2(6.205f, 14.23f), "assets/hud/AbilityGem1.png");
		
		
		stage.addActor(bg);
		
		stage.addActor(emptyheartIcon1);
		stage.addActor(emptyheartIcon2);
		stage.addActor(emptyheartIcon3);
		stage.addActor(heartIcon1);
		stage.addActor(heartIcon2);
		stage.addActor(heartIcon3);
		
		stage.addActor(emptyAbilityCrystal);
		stage.addActor(abilityCrystal);
		
		stage.addActor(currentIcon);
	}
	
	public static void setHP(int hp) {
		if(hp <= 3 && hp >= 0)
			health = hp;
		if(hp == 3) {
			stage.addAction(Actions.targeting(heartIcon3, Actions.alpha(1)));
		}
		if(hp == 2) {
			stage.addAction(Actions.targeting(heartIcon3, Actions.alpha(0)));
			stage.addAction(Actions.targeting(heartIcon2, Actions.alpha(1)));
		}
		if(hp == 1) {
			stage.addAction(Actions.targeting(heartIcon3, Actions.alpha(0)));
			stage.addAction(Actions.targeting(heartIcon2, Actions.alpha(0)));
			stage.addAction(Actions.targeting(heartIcon1, Actions.alpha(1)));
		}
		if(hp == 0) {
			stage.addAction(Actions.targeting(heartIcon3, Actions.alpha(0)));
			stage.addAction(Actions.targeting(heartIcon2, Actions.alpha(0)));
			stage.addAction(Actions.targeting(heartIcon1, Actions.alpha(0)));
		}
	}
	
	
	public static void setAbility(playerForm state) {
		stage.addAction(Actions.removeActor(currentIcon));
		currentForm = state;
		if(currentForm == playerForm.PAWN)
			currentIcon = pawnIcon;
		if(currentForm == playerForm.ROOK)
			currentIcon = rookIcon;
		if(currentForm == playerForm.KNIGHT)
			currentIcon = knightIcon;
		if(currentForm == playerForm.BISHOP)
			currentIcon = bishopIcon;
		
		stage.addActor(currentIcon);
	}
	
	public static void setCharge(boolean active) {
		if(active)
			stage.addAction(Actions.targeting(abilityCrystal, Actions.alpha(1)));
		else
			stage.addAction(Actions.targeting(abilityCrystal, Actions.alpha(0)));
	}
}