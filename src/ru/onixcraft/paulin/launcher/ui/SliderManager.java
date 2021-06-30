package ru.onixcraft.paulin.launcher.ui;

import javafx.animation.FadeTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.SequentialTransitionBuilder;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.util.Duration;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SliderManager {

    private static double duration = 1.2;
    private static HBox slider;

    public SliderManager(GridPane pane, Image img1, Image img2, Image img3, Image img4) {

        init(pane, img1, img2, img3, img4);

    }

    private void init(GridPane pane, Image img1, Image img2, Image img3, Image img4) {
        slider = new HBox();
        GridPane.setVgrow(slider, Priority.ALWAYS);
        GridPane.setHgrow(slider, Priority.ALWAYS);
        GridPane.setValignment(slider, VPos.CENTER);
        GridPane.setHalignment(slider, HPos.RIGHT);
        slider.setSpacing(3);
        slider.setMinHeight(150);
        slider.setMaxHeight(150);
        slider.setMaxWidth(435);
        slider.setMinWidth(435);
        slider.setId("sliderPane");

        final ImageView imageView = new ImageView();
        imageView.fitHeightProperty().bind(slider.heightProperty());
        imageView.fitWidthProperty().bind(slider.widthProperty());
        imageView.setImage(img1);

        final SequentialTransition transitionImg1 = createTransition(imageView, img1);
        final SequentialTransition transitionImg2 = createTransition(imageView, img2);
        final SequentialTransition transitionImg3 = createTransition(imageView, img3);
        final SequentialTransition transitionImg4 = createTransition(imageView, img4);

        slider.getChildren().add(imageView);

        ScheduledExecutorService ses = Executors.newSingleThreadScheduledExecutor();
        ses.scheduleWithFixedDelay(new Runnable() {
            int i = 1;
            @Override
            public void run() {
                switch (i) {
                    case 1:
                        transitionImg2.play();
                        break;
                    case 2:
                        transitionImg3.play();
                        break;
                    case 3:
                        transitionImg4.play();
                        break;
                    case 4:
                        transitionImg1.play();
                        break;
                }
                i = i >= 4 ? 1 : i + 1;
            }
        }, 15L, 15L, TimeUnit.SECONDS);

        pane.getChildren().add(slider);
    }

    SequentialTransition createTransition(final ImageView iv, final Image img) {
        FadeTransition fadeOutTransition
                = new FadeTransition(Duration.seconds(duration), iv);
        fadeOutTransition.setFromValue(1.0);
        fadeOutTransition.setToValue(0.0);
        fadeOutTransition.setOnFinished(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent arg0) {
                iv.setImage(img);
            }

        });

        FadeTransition fadeInTransition
                = new FadeTransition(Duration.seconds(duration), iv);
        fadeInTransition.setFromValue(0.0);
        fadeInTransition.setToValue(1.0);
        SequentialTransition sequentialTransition
                = SequentialTransitionBuilder
                .create()
                .children(fadeOutTransition, fadeInTransition)
                .build();

        return sequentialTransition;
    }

    public static void setDuration(double d){
        duration = d;
    }

    public static void setVisible(boolean visible) {
        slider.setVisible(visible);

    }


}
