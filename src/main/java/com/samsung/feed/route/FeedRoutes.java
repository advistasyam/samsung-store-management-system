package com.samsung.feed.route;

import com.samsung.feed.handler.FeedHandler;
import ratpack.func.Action;
import ratpack.handling.Chain;

import javax.inject.Inject;

public class FeedRoutes implements Action<Chain> {
  @Inject
  private FeedHandler feedHandler;

  @Override
  public void execute(Chain chain) {
    chain
        .path("", ctx -> ctx.byMethod(method -> {
          method
              .get(feedHandler::getFeeds)
              .post(feedHandler::postFeeds);
        }))
        .path(":id", ctx -> ctx.byMethod(method -> {
          method
              .get(feedHandler::getFeedById);
        }));
  }
}
