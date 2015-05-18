import org.newdawn.slick.*;

class PlaneGame extends BasicGame {
    private Image land;
    private Image plane;
    private float landX;
    private float landY;
    private float x;
    private float y;
    private float scale;

    private PlaneGame() {
        super("Plane Game");
    }

    public static void main(String[] args) throws SlickException {
        AppGameContainer app = new AppGameContainer(new PlaneGame());

        app.setDisplayMode(800, 600, false);
        app.start();
    }

    @Override
    public void init(GameContainer gc) throws SlickException {
        land = new Image("/data/land.jpg");
        plane = new Image("/data/plane.png");
        x = 400;
        y = 300;
        landX = 0;
        landY = 0;
        scale = 1.0f;
    }

    @Override
    public void update(GameContainer gc, int delta) throws SlickException {
        Input input = gc.getInput();

        float hip = 0.4f * delta;

        if (input.isKeyDown(Input.KEY_A)) {
            x -= hip;
        }

        if (input.isKeyDown(Input.KEY_D)) {
            x += hip;
        }

        if (input.isKeyDown(Input.KEY_W)) {
            y -= hip;// * Math.cos(Math.toRadians(rotation));
        }

        if (input.isKeyDown(Input.KEY_S)) {
            y += hip;
        }

        Bullet.processBullets(x, y, delta, input);

        scrollLand(delta);
    }

    private void scrollLand(int delta) {
        landY += 0.3f * delta;
        if (landY >= 600) landY = 0;
    }

    @Override
    public void render(GameContainer gc, Graphics g) throws SlickException {
        land.draw(landX, landY);
        land.draw(landX, landY - 600);
        plane.draw(x, y, scale);
        Bullet.removeUnseenBullets();
    }

}