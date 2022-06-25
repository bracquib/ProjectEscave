package info3.game;

public class Camera {
	Avatar followedAvatar;
	View view;
	Vec2 offset;

	public Camera(Avatar a, View v) {
		this.followedAvatar = a;
		this.view = v;
		this.offset = new Vec2(0, 0);
	}

	public Vec2 getPos() {
		if (this.followedAvatar != null) {
			return this.followedAvatar.getPosition()
					.add(new Vec2(-this.view.getWidth() / 2, -this.view.getHeight() / 2).add(this.offset));
		} else {
			return new Vec2(0);
		}
	}

	public void setAvatar(Avatar avatar) {
		this.followedAvatar = avatar;
	}
	
	public void setOffset(Vec2 offset) {
		this.offset = offset;
	}
}
