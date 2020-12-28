package com.gdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gdx.game.GdxGame;
import com.gdx.game.manager.CameraManager;
import com.gdx.game.manager.RessourceManager;
import com.gdx.game.screen.transition.effects.TransitionEffect;

import java.util.ArrayList;
import java.util.List;

public class BaseScreen implements Screen {
    protected final GdxGame gdxGame;
    protected RessourceManager ressourceManager;
    protected OrthographicCamera gameCam;
    protected OrthographicCamera battleCam;
    // viewport that keeps aspect ratios of the game when resizing
    protected Viewport viewport;
    // main stage of each screen
    protected Stage stage;

    public BaseScreen(GdxGame gdxGame, RessourceManager ressourceManager) {
        this.gdxGame = gdxGame;
        this.ressourceManager = ressourceManager;

        CameraManager cameraManager = new CameraManager();
        gameCam = cameraManager.createCamera(Gdx.graphics.getWidth()/3, Gdx.graphics.getHeight()/3, .4f);
        battleCam = cameraManager.createCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), 1);
        // the game will retain it's scaled dimensions regardless of resizing
        viewport = new StretchViewport(gameCam.viewportWidth, gameCam.viewportHeight, gameCam);
        stage = new Stage(viewport, gdxGame.getBatch());
    }

    public void setScreenWithTransition(Screen current, Screen next, List<TransitionEffect> transitionEffect) {
        ArrayList<TransitionEffect> effects = new ArrayList<>(transitionEffect);

        Screen transitionScreen = new TransitionScreen(gdxGame, current, next, effects);
        gdxGame.setScreen(transitionScreen);
    }

    public void createButton(String buttonName, float posX, float posY, Table table) {
        TextureRegion[][] playButtons = ressourceManager.button;

        BitmapFont pixel10 = ressourceManager.pixel10;

        TextureRegionDrawable imageUp = new TextureRegionDrawable(playButtons[0][0]);
        TextureRegionDrawable imageDown = new TextureRegionDrawable(playButtons[1][0]);

        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle(imageUp, imageDown, null, pixel10);
        TextButton button = new TextButton(buttonName, buttonStyle);
        button.getLabel().setColor(new Color(79 / 255.f, 79 / 255.f, 117 / 255.f, 1));

        table.add(button).padLeft(posX).padTop(posY);
        table.row();
    }

    @Override
    public void show() {
        // Nothing
    }

    @Override
    public void render(float delta) {
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        // Nothing
    }

    @Override
    public void pause() {
        // Nothing
    }

    @Override
    public void resume() {
        // Nothing
    }

    @Override
    public void hide() {
        // Nothing
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    public OrthographicCamera getGameCam() {
        return gameCam;
    }

    public OrthographicCamera getBattleCam() {
        return battleCam;
    }

    public Stage getStage() {
        return stage;
    }
}
