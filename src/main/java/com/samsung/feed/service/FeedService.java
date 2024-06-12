package com.samsung.feed.service;

import com.samsung.entity.Feed;
import com.samsung.feed.repository.FeedRepository;
import ratpack.service.Service;

import javax.inject.Inject;
import java.util.List;

public class FeedService implements Service {
  @Inject
  private FeedRepository feedRepository;

  public Feed findById(Long id) {
    return feedRepository.getById(id);
  }

  public List<Feed> findAll() {
    return feedRepository.getAll();
  }

  public void insert(Feed feed) {
    feedRepository.insert(feed);
  }
}
