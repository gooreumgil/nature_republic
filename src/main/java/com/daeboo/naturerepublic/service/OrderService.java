package com.daeboo.naturerepublic.service;

import com.daeboo.naturerepublic.domain.*;
import com.daeboo.naturerepublic.dto.OrderItemDto;
import com.daeboo.naturerepublic.dto.ReviewDto;
import com.daeboo.naturerepublic.dto.ReviewDtoWrapper;
import com.daeboo.naturerepublic.repository.CommentRepository;
import com.daeboo.naturerepublic.repository.ItemRepository;
import com.daeboo.naturerepublic.repository.MemberRepository;
import com.daeboo.naturerepublic.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;
    private final CommentRepository commentRepository;

    public static String uploadDirectory = "C:\\Users\\hunte\\dev\\nature_republic\\src\\main\\resources\\static\\upload";

    @Transactional
    public Order order(OrderItemDto.Create orderItemDto) {

        // 엔티티 조회
        Member member = memberRepository.findById(orderItemDto.getMemberId()).get();
        Item item = itemRepository.findById(orderItemDto.getItemId()).get();

        // 배송정보 생성
        Delivery delivery = Delivery.createDelivery(orderItemDto);

        int orderPrice = orderItemDto.getPrice();
        Integer discount = orderItemDto.getDiscount();
        int count = orderItemDto.getCount();
        Integer savePoints = orderItemDto.getSavePoints();
        Integer usePoints = orderItemDto.getUsePoints();

        // 주문상품생성
        OrderItem orderItem = OrderItem.createOrderItem(item, orderPrice, discount, count);

        // 주문생성
        Order order = Order.createOrder(member, delivery, savePoints, usePoints, orderItem);

        // 포인트 차감
        member.minusPoints(usePoints);

        return orderRepository.save(order);

    }

    public Long onGoingCount(Long memberId) {
        return orderRepository.countDeliveryOngoing(memberId);
    }

    public Order findById(Long id) {
        return orderRepository.findById(id).get();
    }

    @Transactional
    public void orderComplete(Long orderId, Long memberId, boolean isReview) {

        Order order = orderRepository.findById(orderId).get();
        order.orderComplete();

        Delivery delivery = order.getDelivery();
        delivery.deliveryArrived();

        Member member = memberRepository.findById(memberId).get();

        Integer savePoints = order.getSavePoints();
        member.addPoints(savePoints);

    }

//    @Transactional
//    public void orderCompleteWithReview(ReviewDto reviewDto) {
//
//        Member member = memberRepository.findById(reviewDto.getMemberId()).get();
//        Item item = itemRepository.findById(reviewDto.getItemId()).get();
//
//        List<MultipartFile> srcs = reviewDto.getSrcs();
//        List<String> imgPath = new ArrayList<>();
//
//        List<ItemSrc> itemSrcReviews = new ArrayList<>();
//
//        Comment comment = Comment.createCommentTypeReview(reviewDto, item, member, reviewDto.getRating());
//
//        if (!srcs.isEmpty()) {
//
//            srcs.forEach(img -> {
//
//                StringBuilder fileName = new StringBuilder();
//                fileName.append(img.getOriginalFilename() + " ");
//
//                createFile(img);
//
//                imgPath.add(fileName.toString());
//            });
//
//            imgPath.forEach(s -> {
//                ItemSrc itemSrcReview = ItemSrc.createItemSrcReview(item, s);
//                itemSrcReviews.add(itemSrcReview);
//            });
//
//        }
//
//        Comment savedComment = commentRepository.save(comment);
//        Comment findComment = commentRepository.findById(savedComment.getId()).get();
//
//        if (!itemSrcReviews.isEmpty()) {
//            itemSrcReviews.forEach(itemSrc -> {
//                findComment.addItemSrc(itemSrc);
//            });
//        }
//
//        if (reviewDto.getOrderId() != null) {
//            Order order = orderRepository.findById(reviewDto.getOrderId()).get();
//            order.orderComplete();
//
//            Delivery delivery = order.getDelivery();
//            delivery.deliveryArrived();
//
//            Integer savePoints = order.getSavePoints();
//
//            if (savePoints == null) {
//                member.addPoints(15);
//            } else {
//                member.addPoints(order.getSavePoints() + 15);
//            }
//
//        }
//    }

    @Transactional
    public void orderCompleteWithReviewAll(ReviewDtoWrapper wrapper) {

        List<ReviewDto> reviewDtos = wrapper.getReviewDtos();

        Member member = memberRepository.findById(wrapper.getMemberId()).get();

        for (ReviewDto reviewDto : reviewDtos) {

            List<MultipartFile> srcs = reviewDto.getSrcs();
            List<String> remove = reviewDto.getRemove();

            Item item = itemRepository.findById(reviewDto.getItemId()).get();

            Comment comment = Comment.createCommentTypeReview(reviewDto, item, member);

            if (!srcs.isEmpty()) {
                srcs.remove(srcs.size() - 1);
            }

            if (!remove.isEmpty()) {
                for (String s : remove) {
                    srcs.removeIf(x -> x.getOriginalFilename().equals(s));
                }
            }

            List<String> imgPath = new ArrayList<>();
            List<ItemSrc> itemSrcReviews = new ArrayList<>();

            if (!srcs.isEmpty()) {

                srcs.forEach(img -> {

                    StringBuilder fileName = new StringBuilder();
                    fileName.append(img.getOriginalFilename() + " ");

                    createFile(img);
                    imgPath.add(fileName.toString());
                });

                imgPath.forEach(path -> {

                    ItemSrc itemSrcReview = ItemSrc.createItemSrcReview(item, path);
                    itemSrcReviews.add(itemSrcReview);
                });
            }

            Comment savedComment = commentRepository.save(comment);

            if (!itemSrcReviews.isEmpty()) {
                itemSrcReviews.forEach(itemSrc -> {
                    savedComment.addItemSrc(itemSrc);
                });
            }
        }

        Order order = orderRepository.findById(wrapper.getOrderId()).get();
        order.orderComplete();

        Delivery delivery = order.getDelivery();
        delivery.deliveryArrived();

        Integer savePoints = order.getSavePoints();

        if (savePoints == null) {
            member.addPoints(15);
        } else {
            member.addPoints(savePoints + 15);
        }

    }

    private void createFile(MultipartFile img) {

        Path fileNameAndPath = Paths.get(uploadDirectory, img.getOriginalFilename());

        try {
            Files.write(fileNameAndPath, img.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
