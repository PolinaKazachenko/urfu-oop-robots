package org.example.gui;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Класс для обновления состояния игры
 */
public class GameController {
    private GameModel gameModel;
    private GameVisualizer gameVisualizer;
    private final Timer m_timer = initTimer();

    public GameController(GameModel gameModel, GameVisualizer gameVisualizer) {
        this.gameModel = gameModel;
        this.gameVisualizer = gameVisualizer;
        m_timer.schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                gameVisualizer.onRedrawEvent();
            }
        }, 0, 50);
        m_timer.schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                gameVisualizer.updateCoordinates();
                gameModel.onModelUpdateEvent();
            }
        }, 0, 10);
    }
    protected static Timer initTimer()
    {
        Timer timer = new Timer("events generator", true);
        return timer;
    }
}
