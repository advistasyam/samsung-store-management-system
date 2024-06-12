package com.samsung.feed.handler;

import com.samsung.entity.Admin;
import com.samsung.entity.Feed;
import com.samsung.feed.service.FeedService;
import com.samsung.util.auth.AuthUtils;
import com.samsung.util.response.ResponseUtil;
import ratpack.handling.Context;
import ratpack.jackson.Jackson;

import javax.inject.Inject;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class FeedHandler {
  @Inject
  private AuthUtils authUtils;
  @Inject
  private FeedService feedService;

  public void getFeeds(Context ctx) {
    List<Feed> feeds = feedService.findAll();
    ResponseUtil.okResponse(ctx, feeds);
  }

  public void postFeeds(Context ctx) throws Exception {
    if (authUtils.isNotLogin(ctx)) {
      return;
    }
    Admin admin = authUtils.parseToken(ctx);
    ctx.parse(Jackson.fromJson(Feed.class)).then(feed -> {
      feed.setCreatedAt(new Timestamp(new Date().getTime()));
      feed.setAdmin(admin);
      feedService.insert(feed);
      ResponseUtil.createdResponse(ctx, feed);
    });
  }

  public void getFeedById(Context ctx) {
    Long id = Long.valueOf(ctx.getPathTokens().get("id"));
    Feed feed = feedService.findById(id);
    ResponseUtil.okResponse(ctx, feed);
  }
}
