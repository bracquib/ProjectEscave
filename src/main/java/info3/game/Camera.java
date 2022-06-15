package info3.game;

public class Camera {
	Avatar followedAvatar;

	public Camera(Avatar a) {
		this.followedAvatar = a;
	}

	public Vec2 getPos() {
		if (this.followedAvatar != null) {
			// TODO: remove hardcoded offset
			return this.followedAvatar.getPosition().add(new Vec2(-528, -368));
		} else {
			return new Vec2(0);
		}
	}

	public void setAvatar(Avatar avatar) {
		this.followedAvatar = avatar;
	}
}
