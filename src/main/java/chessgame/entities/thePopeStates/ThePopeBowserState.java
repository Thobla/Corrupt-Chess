package chessgame.entities.thePopeStates;

import chessgame.entities.ThePope;

public class ThePopeBowserState extends ThePopeStates {
	ThePope pope;
	public ThePopeBowserState(ThePope pope) {
		this.pope = pope;
	}

	@Override
	public void Enter() {
		pope.useMagicCircle = false;
		pope.magicLock = false;
		pope.finishMagicCircle = true;
		pope.magicCircleAttack(pope.magicRoations);
	}

	@Override
	public void Update() {
		if(pope.finishMagicCircle && pope.magicLock)
			pope.changeState(pope.idleState);
	}

}
