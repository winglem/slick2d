import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class Bullet {
    private static final Object lockObj = new Object();
    private static final List<Bullet> bullets = Collections.synchronizedList(new ArrayList<Bullet>());
    private static Image bulletSourceImage;
    private static float deltaSinceLastBulletShot = 0;
    private float x;
    private float y;
    private Image bullet;

    static void processBullets(float x, float y, int delta, Input input) throws SlickException {
        deltaSinceLastBulletShot += delta;

        if (deltaSinceLastBulletShot > 200 && input.isKeyDown(Input.KEY_SPACE)) {
            addNewBullet(x, y, delta);
            deltaSinceLastBulletShot = 0;
        }
        for (Bullet bullet : bullets) {
            bullet.setPosition(delta);
        }
    }

    static private void addNewBullet(float x, float y, int delta) throws SlickException {
        Bullet bullet = new Bullet();
        bullet.x = x + 60;
        bullet.y = y + 60;
        bullet.setPosition(delta);
        bullets.add(bullet);
    }

    static void removeUnseenBullets() {
        for (int x = 0; x < bullets.size(); x++) {
            Bullet bullet = bullets.get(x);
            if ((bullet.x > 800) || (bullet.y > 600) || (bullet.x < 0) || (bullet.y < 0)) {
                bullets.remove(bullet);
            } else {
                bullet.draw();
            }
        }
    }

    private void setPosition(int delta) throws SlickException {
        synchronized (lockObj) {
            if (bulletSourceImage == null) {
                bulletSourceImage = new Image("/data/bullet.png");
            }
        }

        if (bullet == null) {
            bullet = bulletSourceImage.copy();
        }

        float hip = 0.8f * delta;

        y -= hip;

    }

    private void draw() {
        bullet.draw(x, y);
    }
}

