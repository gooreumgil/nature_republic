package com.daeboo.naturerepublic.service;

import com.daeboo.naturerepublic.domain.*;
import com.daeboo.naturerepublic.dto.OrderItemDtoWrapper;
import com.daeboo.naturerepublic.dto.ReviewDto;
import com.daeboo.naturerepublic.dto.ReviewDtoWrapper;
import com.daeboo.naturerepublic.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    private final ReviewRepository reviewRepository;

    public static String uploadDirectory = "C:\\Users\\hunte\\dev\\nature_republic\\src\\main\\resources\\static\\upload";

//    @Transactional
//    public Order order(OrderItemDto.Create orderItemDto) {
//
//        // 엔티티 조회
//        Member member = memberRepository.findById(orderItemDto.getMemberId()).get();
//        Item item = itemRepository.findById(orderItemDto.getItemId()).get();
//
//        // 배송정보 생성
//        Delivery delivery = Delivery.createDelivery(orderItemDto);
//
//        int orderPrice = orderItemDto.getPrice();
//        Integer discount = orderItemDto.getDiscount();
//        int count = orderItemDto.getCount();
//        Integer savePoints = orderItemDto.getSavePoints();
//        Integer usePoints = orderItemDto.getUsePoints();
//
//        // 주문상품생성
//        OrderItem orderItem = OrderItem.createOrderItem(item, orderPrice, discount, count);
//
//        // 주문생성
//        Order order = Order.createOrder(member, delivery, savePoints, usePoints, orderItem);
//
//        // 포인트 차감
//        member.minusPoints(usePoints);
//
//        return orderRepository.save(order);
//
//    }

    @Transactional
    public Orders order(OrderItemDtoWrapper orderWrapper) {

        Member member = memberRepository.findById(orderWrapper.getMemberId()).get();
        Delivery delivery = Delivery.createDelivery(orderWrapper);

        int savePoints = orderWrapper.getSavePoints();
        Integer usePoints = orderWrapper.getUsePoints();

        List<OrderItem> orderItems = new ArrayList<>();

        orderWrapper.getOrderItemDtos().forEach(create -> {

            Item item = itemRepository.findById(create.getItemId()).get();
            int orderPrice = create.getOrderPrice();
            Integer discount = create.getDiscount();
            int count = create.getCount();

            OrderItem orderItem = OrderItem.createOrderItem(item, orderPrice, discount, count);
            orderItems.add(orderItem);

        });

        Orders orders = Orders.createOrder(member, delivery, savePoints, usePoints, orderItems);

        member.minusPoints(usePoints);

        return orderRepository.save(orders);


    }

    public Long onGoingCount(Long memberId) {
        return orderRepository.countDeliveryOngoing(memberId);
    }

    public Orders findById(Long id) {
        return orderRepository.findById(id).get();
    }

    public Orders findByIdQuery(Long id) {
        return orderRepository.findByIdQuery(id).orElseThrow(() -> new RuntimeException("존재하지 않는 Order입니다."));
    }

    @Transactional
    public void orderComplete(Long orderId, Long memberId, boolean isReview) {

        Orders orders = orderRepository.findById(orderId).get();
        orders.orderComplete();

        Delivery delivery = orders.getDelivery();
        delivery.deliveryArrived();

        Member member = memberRepository.findById(memberId).get();

        Integer savePoints = orders.getSavePoints();

        if (savePoints != null) {
            member.addPoints(savePoints);
        } else {
            member.addPoints(0);
        }
    }

    @Transactional
    public void orderCompleteWithReviewAll(ReviewDtoWrapper wrapper) {

        List<ReviewDto> reviewDtos = wrapper.getReviewDtos();

        Member member = memberRepository.findById(wrapper.getMemberId()).get();

        for (ReviewDto reviewDto : reviewDtos) {

            List<MultipartFile> srcs = reviewDto.getSrcs();
            List<String> remove = reviewDto.getRemove();

            Item item = itemRepository.findById(reviewDto.getItemId()).get();

            Review review = Review.createReview(reviewDto, item, member);
//            Comment comment = Comment.createCommentTypeReview(reviewDto, item, member);

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

//            Comment savedComment = commentRepository.save(comment);
            Review savedReview = reviewRepository.save(review);

            if (!itemSrcReviews.isEmpty()) {
                itemSrcReviews.forEach(itemSrc -> {
                    savedReview.addItemSrc(itemSrc);
                });
            }
        }

        Orders orders = orderRepository.findById(wrapper.getOrderId()).get();
        orders.orderComplete();

        Delivery delivery = orders.getDelivery();
        delivery.deliveryArrived();

        Integer savePoints = orders.getSavePoints();

        if (savePoints == null) {
            member.addPoints(15);
        } else {
            member.addPoints(savePoints + 15);
        }

    }

    public Page<Orders> findByMemberId(Long id, Pageable pageable) {
        Page<Orders> orders = orderRepository.findAllByMemberId(id, pageable);
        return orders;
    }

    public Page<Orders> findAllPages(Pageable pageable) {
        return orderRepository.findAll(pageable);
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
