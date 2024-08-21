package amcn.amcn.config;

import amcn.amcn.cardnews.domain.cardnews.CardNews;
import amcn.amcn.cardnews.repository.CardNewsRepository;
import amcn.amcn.comment.domain.comment.Comment;
import amcn.amcn.comment.repository.CommentRepository;
import amcn.amcn.community.domain.board.Board;
import amcn.amcn.community.repository.BoardRepository;
import amcn.amcn.like.repository.LikeRepository;
import amcn.amcn.member.domain.member.Member;
import amcn.amcn.member.domain.member.MemberType;
import amcn.amcn.member.domain.member.RoleType;
import amcn.amcn.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;


//@Component
@RequiredArgsConstructor
public class DataInitializer3 {

    private final MemberRepository memberRepository;

    private final BoardRepository boardRepository;

    private final CardNewsRepository cardNewsRepository;

    private final CommentRepository commentRepository;

    private final LikeRepository likeRepository;
    @Bean
    public CommandLineRunner initDatabase() {
        return args -> {
            // 서버 시작 시 데이터베이스에 초기값을 삽입합니다.
            initializeMembers();
        };
    }

    @Transactional
    public void initializeMembers() {
        // 데이터베이스 초기화를 위한 코드 작성
        Member member0 = new Member(
                "6924f10f-6b4d-437e-831f-88c8b6dec180",
                "chltmdgh522",
                "$2a$10$Bmhj8oQATwHAVPedShSjAOvBdx/WUCW0f.KVynRaKQJNjoy6JGh0K",
                "chltmdgh517@naver.com",
                LocalDate.of(2000, 5, 22),
                LocalDate.of(2022, 7, 2),
                "basic2.png",
                RoleType.MASTER,
                MemberType.남자,
                "최승호",
                99999L
        );

        Member member1 = new Member(
                "6924f10f-6b4d-437e-831f-88c8b6dec181",
                "test1",
                "$2a$10$Bmhj8oQATwHAVPedShSjAOvBdx/WUCW0f.KVynRaKQJNjoy6JGh0K",
                "test1@naver.com",
                LocalDate.of(2000, 5, 31),
                LocalDate.of(2022, 7, 2),
                "basic2.png",
                RoleType.USER,
                MemberType.남자,
                "석재민",
                100L
        );

        // 데이터베이스 초기화를 위한 코드 작성
        Member member2 = new Member(
                "6924f10f-6b4d-437e-831f-88c8b6dec182",
                "test2",
                "$2a$10$Bmhj8oQATwHAVPedShSjAOvBdx/WUCW0f.KVynRaKQJNjoy6JGh0K",
                "test2@naver.com",
                LocalDate.of(2001, 7, 2),
                LocalDate.of(2024, 6, 1),
                "basic2.png",
                RoleType.AUTHUSER,
                MemberType.남자,
                "김경진",
                100L
        );

        // 데이터베이스 초기화를 위한 코드 작성
        Member member3 = new Member(
                "6924f10f-6b4d-437e-831f-88c8b6dec183",
                "test3",
                "$2a$10$Bmhj8oQATwHAVPedShSjAOvBdx/WUCW0f.KVynRaKQJNjoy6JGh0K",
                "test3@naver.com",
                LocalDate.of(2000, 7, 2),
                LocalDate.of(2023, 9, 2),
                "basic2.png",
                RoleType.AUTHUSER,
                MemberType.남자,
                "이민수",
                100L
        );

        Member member4 = new Member(
                "6924f10f-6b4d-437e-831f-88c8b6dec184",
                "test4",
                "$2a$10$Bmhj8oQATwHAVPedShSjAOvBdx/WUCW0f.KVynRaKQJNjoy6JGh0K",
                "test4@naver.com",
                LocalDate.of(1999, 6, 22),
                LocalDate.of(2023, 9, 2),
                "basic2.png",
                RoleType.AUTHUSER,
                MemberType.남자,
                "김성학",
                500L
        );

        Member member5 = new Member(
                "6924f10f-6b4d-437e-831f-88c8b6dec185",
                "test5",
                "$2a$10$Bmhj8oQATwHAVPedShSjAOvBdx/WUCW0f.KVynRaKQJNjoy6JGh0K",
                "test5@naver.com",
                LocalDate.of(2000, 7, 22),
                LocalDate.of(2024, 7, 3),
                "basic2.png",
                RoleType.AUTHUSER,
                MemberType.여자,
                "김정연",
                1L
        );


        Member member6 = new Member(
                "6924f10f-6b4d-437e-831f-88c8b6dec186",
                "test6",
                "$2a$10$Bmhj8oQATwHAVPedShSjAOvBdx/WUCW0f.KVynRaKQJNjoy6JGh0K",
                "test6@naver.com",
                LocalDate.of(1998, 11, 24),
                LocalDate.of(2024, 7, 2),
                "basic2.png",
                RoleType.AUTHUSER,
                MemberType.여자,
                "홍세현",
                9999L
        );


        Member member7 = new Member(
                "6924f10f-6b4d-437e-831f-88c8b6dec187",
                "test7",
                "$2a$10$Bmhj8oQATwHAVPedShSjAOvBdx/WUCW0f.KVynRaKQJNjoy6JGh0K",
                "test7@naver.com",
                LocalDate.of(2003, 11, 13),
                LocalDate.of(2024, 7, 2),
                "basic2.png",
                RoleType.AUTHUSER,
                MemberType.여자,
                "송동화",
                250L
        );


        Member member8 = new Member(
                "6924f10f-6b4d-437e-831f-88c8b6dec188",
                "test8",
                "$2a$10$Bmhj8oQATwHAVPedShSjAOvBdx/WUCW0f.KVynRaKQJNjoy6JGh0K",
                "test8@naver.com",
                LocalDate.of(1999, 12, 31),
                LocalDate.of(2023, 9, 2),
                "basic2.png",
                RoleType.AUTHUSER,
                MemberType.여자,
                "김재림",
                10L
        );


        Member member9 = new Member(
                "6924f10f-6b4d-437e-831f-88c8b6dec189",
                "test9",
                "$2a$10$Bmhj8oQATwHAVPedShSjAOvBdx/WUCW0f.KVynRaKQJNjoy6JGh0K",
                "test9@naver.com",
                LocalDate.of(2006, 7, 26),
                LocalDate.of(2024, 4, 12),
                "basic2.png",
                RoleType.AUTHUSER,
                MemberType.남자,
                "윤웅희",
                150L
        );


        Member member10 = new Member(
                "6924f10f-6b4d-437e-831f-88c8b6dec190",
                "test10",
                "$2a$10$Bmhj8oQATwHAVPedShSjAOvBdx/WUCW0f.KVynRaKQJNjoy6JGh0K",
                "test10@naver.com",
                LocalDate.of(2002, 7, 2),
                LocalDate.of(2023, 9, 2),
                "basic2.png",
                RoleType.AUTHUSER,
                MemberType.남자,
                "안세준",
                100L
        );








        Board board1 = new Board(
                member1,
                "코딩? 프로그래밍?",
                "우선 코딩과 프로그래밍의 차이도 모릅니다.\n" +
                        "블라인드 네이버 구글을 통해서 검색을 해봤지만\n" +
                        "제 지식이 부족하여 여기에 다시 글을 씁니다.\n" +
                        "\n" +
                        "목표는 간단한 앱 만들기\n" +
                        "1. 웹 로그인 후 메뉴를 찾아 들어가서 스케쥴 및 기타 연관 자료확인 후 다운로드 및 저장\n" +
                        "2. 스케쥴 및 연관 자료 통합\n" +
                        "3. 통합된 자료를 구글 캘린더에 연동\n" +
                        "4. 사용자간의 스케쥴 공유\n" +
                        "5. 기타 간단한 계산 기능 및 알람 기능\n" +
                        "\n" +
                        "궁금증\n" +
                        "1. 어떤 언어를 써야할까요?\n" +
                        "파이썬을 많이 추천하시던데 C나 자바부터 하라는 분도 계시네요\n" +
                        "\n" +
                        "2. 공부방법은?\n" +
                        "책 유투브 구글링을 통한 독학을 고려하고 있습니다\n" +
                        "스케쥴 근무라 학원이나 방통대 등은 힘들것 같아요\n" +
                        "추천 교재나 사이트가 있을까요?\n" +
                        "\n" +
                        "3. 노트북?\n" +
                        "휴대성 때문에 그램을 생각 중인데\n" +
                        "어느정도 사양이 되어야 할까요?",
                false,
                "IT",
                "O",
                "O",
                8,
                LocalDate.of(2024,5,28),
                LocalDate.of(2024,5,28)
        );

        Board board2 = new Board(
                member2,
                "IT 기업도 학벌보나요?",
                "안녕하세요,\n" +
                        "기획에서 DS 쪽으로 직무변경하려고 대학원 다니고 있는 1인입니다.\n" +
                        "\n" +
                        "IT기업은 학벌보다는 실력이라고 알고있는데, IT 기업들도 학벌 많이 보나요??\n" +
                        "미국대학 같은경우 어느정도 수준으로 나와야 취업이 가능할까요?\n" +
                        "\n!",
                false,
                "IT",
                "O",
                "O",
                110,
                LocalDate.of(2024,5,29),
                LocalDate.of(2024,5,29)
        );


        Board board3 = new Board(
                member3,
                "IT 기업 랭킹",
                "1. 외국 1티어 (연봉 1억 이상)\n" +
                        "- FAANG\n" +
                        "페이스북 애플 아마존 넷플릭스 구글\n" +
                        "\n" +
                        "2. 국내 1티어 (연봉 8천-1억)\n" +
                        "- 네카라쿠배\n" +
                        "네이버 카카오 라인 쿠팡 배민\n" +
                        "\n" +
                        "3. 국내 탑유니콘 (연봉 연봉7천-1억)\n" +
                        "- 토두당직야\n" +
                        "토스 두나무 당근마켓 직방 야놀자\n" +
                        "\n" +
                        "4. 4대 게임사 (연봉 7천 이상)\n" +
                        "- 1K 3N\n" +
                        "크래프톤 넥슨 엔씨 넷마블\n" +
                        "\n" +
                        "5. 4대 SI (연봉 8천-1억)\n" +
                        "- 삼엘슥현\n" +
                        "삼성SDS LGCNS SKC&C 현대오토에버",
                false,
                "IT",
                "O",
                "O",
                140,
                LocalDate.of(2024,5,30),
                LocalDate.of(2024,5,30)
        );


        Board board4 = new Board(
                member4,
                "중견 중소 it 연봉인상률",
                "얼마나 되시나요??\n" +
                        "\n" +
                        "it 메이저 신입은 오육천 우습던데\n" +
                        "\n" +
                        "첨부터 짜게주면\n" +
                        "\n" +
                        "인상률 무지 높아야 할거같은데 ㅠㅠ",
                false,
                "IT",
                "O",
                "O",
                250,
                LocalDate.of(2024,6,1),
                LocalDate.of(2024,6,1)
        );


        Board board5 = new Board(
                member5,
                "농협중앙회 IT vs 한국투자증권 IT",
                "안녕하세요... 제 친구가 요번에 농중it랑 한투it를 붙었는데 고민중이더라고요. 현재 서울 거주중인 친구고요.. 일반직이면 닥 농중 추천했을텐데 it는 잘 몰라서요ㅠ 농중it는 의왕이고 한투it는 여의도라고 합니다.\n" +
                        "현직자 분들 있으시면 전체적 분위기나 그 외 추천하거나 비추하는 이유좀 알려주실 수 있을까요? 농중은 급여를 알아서 제가 대충 말해줬는데 한투 급여도 조금 궁금합니다!!",
                false,
                "IT",
                "O",
                "O",
                8,
                LocalDate.of(2024,6,3),
                LocalDate.of(2024,6,3)
        );


        Board board6 = new Board(
                member6,
                "한국거래소 it vs skt it",
                "둘 다 신입이고 어딜 더 추천하시나요?\n" +
                        "\n" +
                        "스크트는 뭐하는지 얼추 아는데요.\n" +
                        "\n" +
                        "거래소 it는 정보가 전혀 없네요.\n" +
                        "거래소 it 전부 코스콤이 하는걸로 아는데\n" +
                        "여기 개발 이런거 안 할듯한데 일 괜찮나요?\n" +
                        "\n" +
                        "거래소분들과 코스콤분들 생각도 궁금합니다!",
                false,
                "IT",
                "O",
                "O",
                616,
                LocalDate.of(2024,6,3),
                LocalDate.of(2024,6,3)

        );
        Board board7 = new Board(
                member7,
                "인공지능 개발자 형들",
                "인공지능 개발자 형들\n" +
                        "난 20살 마이스터고 전형으로 한전 입사한 애기인데\n" +
                        "최근 유튜버 안될과학에서 보이저엑스 남세동 대표가 나와서 딥러닝, 인공지능 이야기를 해주는데 너무 충격이더라고...\n" +
                        "\n" +
                        "그거 보면서 미래에는 세상이 정말 많이 바뀔 것 같고\n" +
                        "세상의 흐름에 나도 올라타고 싶어서 나도 한 번 인공지능 개발을 공부해보고 싶다는 생각이 들었어 ㅎㅎ\n" +
                        "\n" +
                        "말도 안되는거 알지만\n" +
                        "뭐를 어떻게 공부해보는게 좋을까?\n" +
                        "\n" +
                        "c언어, 파이썬 기초만 알아 !\n" +
                        "\n" +
                        "미리 고마워 ㅎㅎ",
                false,
                "IT",
                "O",
                "O",
                786,
                LocalDate.of(2024,6,4),
                LocalDate.of(2024,6,4)

        );

        Board board8 = new Board(
                member8,
                "it 개발자형들 정말 연봉이랑 인상률 높아?",
                "요즘 개발자 몸값 비싼거야 다들 알겠지만\n" +
                        "\n" +
                        "실제로 어느수준인지 궁금하다\n" +
                        "\n" +
                        "특히 네카라쿠배당토 같은 메이저 it보다도\n" +
                        "\n" +
                        "중견 중소 it들도 연봉 높고 인상률 높은지 궁금해 (사실 네카라쿠배당토도 궁금해 ㅋㅋ)\n" +
                        "\n" +
                        "아니면 그냥 메이저 it기업들만 높은 거 가지고 기사쓴느지 궁금해서\n" +
                        "\n" +
                        "나는 비개발 직군인데 들으면 현타오려나 (문과는 웁니다...)\n" +
                        "\n" +
                        "형들 자랑한번부탁해",
                false,
                "IT",
                "O",
                "O",
                855,
                LocalDate.of(2024,6,5),
                LocalDate.of(2024,6,5)

        );

        Board board9 = new Board(
                member9,
                "한국거래소 it vs skt it",
                "둘 다 신입이고 어딜 더 추천하시나요?\n" +
                        "\n" +
                        "스크트는 뭐하는지 얼추 아는데요.\n" +
                        "\n" +
                        "거래소 it는 정보가 전혀 없네요.\n" +
                        "거래소 it 전부 코스콤이 하는걸로 아는데\n" +
                        "여기 개발 이런거 안 할듯한데 일 괜찮나요?\n" +
                        "\n" +
                        "거래소분들과 코스콤분들 생각도 궁금합니다!",
                false,
                "IT",
                "O",
                "O",
                616,
                LocalDate.of(2024,6,3),
                LocalDate.of(2024,6,3)

        );

        Board board10 = new Board(
                member10,
                "기술보증기금 IT",
                "기술보증기금 IT업무 야근이 많나요?\n" +
                        "전부 본점에서 근무라서 어떤가 궁금하네요\n" +
                        "코딩 시험도 잇던데..난이도가 어떤지",
                false,
                "IT",
                "O",
                "O",
                156,
                LocalDate.of(2024,6,5),
                LocalDate.of(2024,6,5)

        );

        Board board11 = new Board(
                member1,
                "스포츠 토토???",
                "흠 제가이런거 잘 모르는데\n" +
                        "남자친구 폰에 카톡이와서봤는데\n" +
                        "유료 정보?받아보는거같아서 캐물었더니\n" +
                        "스포츠 토토 정보받는거라고\n" +
                        "소액만하고 아직까진 다 땃다고하는데\n" +
                        "스포츠토토는 금액제한이있나요??\n" +
                        "도박인거죠..?\n" +
                        "이걸로 인생말아먹은사람 많아요..?\n" +
                        "제가 진짜 모르는 분야라…",
                false,
                "스포츠",
                "O",
                "O",
                56,
                LocalDate.of(2024,6,5),
                LocalDate.of(2024,6,5)

        );

        Board board12 = new Board(
                member2,
                "축구 좋아하는 여자 어떤데",
                "월드컵 아시안컵 유로 .. 다 좋아함\n" +
                        "\n" +
                        "거의 혼자 경기장 다니는데\n" +
                        "같이 다닐 남편.. 어딘가엔 있겠지",
                false,
                "스포츠",
                "O",
                "O",
                202,
                LocalDate.of(2024,6,5),
                LocalDate.of(2024,6,5)

        );

        Board board13 = new Board(
                member3,
                "KT 스포츠",
                "KT 스포츠 신입사원 채용떴던데\n" +
                        "경영전략 사업부에 전략 기획이랑 재무담당이야\n" +
                        "근무지역은 수원이던데\n" +
                        "\n" +
                        "여기 근무환경이나 워라벨어때?\n" +
                        "그리고 중고신입 환영하려나...\n" +
                        "\n" +
                        "이번 직무 아는 형들 정보 좀 부탁드려요 ㅜㅜ",
                false,
                "스포츠",
                "X",
                "O",
                883,
                LocalDate.of(2024,6,5),
                LocalDate.of(2024,6,5)

        );

        Board board14 = new Board(
                member4,
                "스포츠",
                "그다지 공놀이 좋아하지 않는데\n" +
                        "전부 축구하니까 축구를 보는데\n" +
                        "뭘 저리도 몸에 그리고 써 놓았을까…\n" +
                        "유행이라고 해도 참 별로다",
                false,
                "스포츠",
                "X",
                "O",
                5336,
                LocalDate.of(2024,6,5),
                LocalDate.of(2024,6,5)

        );

        Board board15 = new Board(
                member5,
                "운동 스타",
                "직장인 노예들은 투잡으로 돈 벌어서 빠른 은퇴를 바라지만,\n" +
                        "\n" +
                        "스포츠 스타들은 1년 수입만으로 평생을 먹고 살 수 있음에도 은퇴식에 아쉬움의 눈물을 흘리는 것을 보면\n" +
                        "\n" +
                        "스포츠 스타는 본인 직업을 굉장히 만족하고 자부심을 느끼는 것 같네.\n" +
                        "\n" +
                        "빨리 은퇴하고 싶은 노예인 나로서는\n" +
                        "ㅈㄴ 부럽다 ㅅㅂ",
                false,
                "스포츠",
                "X",
                "O",
                526,
                LocalDate.of(2024,6,5),
                LocalDate.of(2024,6,5)

        );

        Board board16 = new Board(
                member6,
                "헬스복 추천좀",
                "추천하는거 뭐이떠?",
                false,
                "스포츠",
                "X",
                "O",
                561,
                LocalDate.of(2024,6,5),
                LocalDate.of(2024,6,5)

        );

        Board board17 = new Board(
                member7,
                "축구 감독의 중요성",
                "난 사실 전술보다 선수능력이 중요하다고 생각했었는데 토트넘보니 감독이 중요하단걸 느꼈다. 전술이 이렇게 중요하다니..\n" +
                        "후방 빌드업 하나밖에 없는 우리 국대 월드컵 이번에도 큰 기대는 안하게 된다",
                false,
                "스포츠",
                "O",
                "O",
                5432,
                LocalDate.of(2024,6,5),
                LocalDate.of(2024,6,5)

        );

        Board board18 = new Board(
                member8,
                "야구 입문하려는데",
                "어느팀으로 가는게 좋징,,?\n" +
                        "여자 혼자 야구 보러가면 좀 그랭?\n" +
                        "\n" +
                        "---------일요일 00:50\n" +
                        "오늘 롯데 엘지전 미쳤던뎅\n" +
                        "이런거 직관하면 이제 야구늪인거겠지\n" +
                        "\n" +
                        "---------토요일 오전 9:20 현상황\n" +
                        "*서울이니 두산, 엘지, 키움 중 가라\n" +
                        ": 잠실이면 두산 가라는게 더 많은듯?\n" +
                        "\n" +
                        "*그냥 한화: 근본(이라는데 왜..?)\n" +
                        "*삼성과 롯데: 듬성\n" +
                        "* 엔씨 쓱 위즈: 가뭄에 콩",
                false,
                "스포츠",
                "O",
                "O",
                422,
                LocalDate.of(2024,6,5),
                LocalDate.of(2024,6,5)

        );

        Board board19 = new Board(
                member9,
                "야구 한화팬들 궁금한거잇음",
                "내 남친이 한화팬이거든? 난 막 입문한 야구뉴비.\n" +
                        "남친이 야구본다하면 키킥 꼴찌 이렇게 놀리는대 슬프고화나지만 반박을 못하겠대\n" +
                        "가끔 이렇게 놀리는거 괜찮을까....?",
                false,
                "스포츠",
                "O",
                "O",
                321,
                LocalDate.of(2024,6,5),
                LocalDate.of(2024,6,5)

        );

        Board board20 = new Board(
                member10,
                "다이어트",
                "살 좀 빼려고 하는데 잘 안됨. 효과적인 다이어트 방법 부탁한다.",
                false,
                "스포츠",
                "X",
                "O",
                222,
                LocalDate.of(2024,6,5),
                LocalDate.of(2024,6,5)

        );


        Board board21 = new Board(
                member1,
                "부업으로 예술 쪽 일 뭐 없을까",
                "어렸을때 동네 학원 다녔을때부터 칭찬 많이 들었는데\n" +
                        "가난해서 포기함 ㅋㅋㅋ...\n" +
                        "근데 나이들어서도 예술쪽 욕구(?)는 불쑥 불쑥 계속 드는거 같은데\n" +
                        "본업이 아니더라도 이런 쪽 소질 기를 수 있을 만한 거 뭐 없을까...\n" +
                        "\n" +
                        "조각쪽은 정말 자신 없고 손 힘조절 못하고\n" +
                        "학원다닐때 이 색 무슨색으로 만들었냐 맞춰봐라 하면 다 맞췄었어 공간지각력도 좋은 편임\n" +
                        "똑같이 따라 그리는건 잘하는편 근데 소묘쪽은 집중 많이해야하고 즐기기보단 좀 힘들게?느껴져\n" +
                        "나이들고 이런 쪽 흥미 가질만한게 화장이나 옷정도라 그나마 이런데 관심갖는편...",
                false,
                "예술",
                "O",
                "O",
                111,
                LocalDate.of(2024,6,5),
                LocalDate.of(2024,6,5)

        );

        Board board22 = new Board(
                member2,
                "미술 학원 vs 미술 과외 vs 미술 모임",
                "취미로 배울건뎅 뭐로 해?",
                false,
                "예술",
                "O",
                "O",
                123,
                LocalDate.of(2024,6,5),
                LocalDate.of(2024,6,5)

        );

        Board board23 = new Board(
                member3,
                "회사에서 주로 무슨 장르 음악 들어?",
                "난 jpop이라 블루투스 연결 해제되면 사회적 매장당함",
                false,
                "예술",
                "O",
                "O",
                121,
                LocalDate.of(2024,6,5),
                LocalDate.of(2024,6,5)

        );

        Board board24 = new Board(
                member4,
                "음악",
                "요즘 일이 정말많이힘듬..\n" +
                        "모든욕구없어(식욕, 성욕, 수면욕)\n" +
                        "음악만 듣는데..\n" +
                        "멜론 90년대 2000년대음악 다 좋다..\n" +
                        "사람냄새, 남자기때문에, 돌아와줘, 체념,,,\n" +
                        "\n" +
                        "진짜 뻥안치고 노래들으며 하늘보면 눈물만난다..",
                false,
                "예술",
                "X",
                "O",
                10,
                LocalDate.of(2024,6,5),
                LocalDate.of(2024,6,5)

        );

        Board board25 = new Board(
                member5,
                "고전 문학 추천해주세요~~",
                "요즘 소설책을\n" +
                        "너무 안읽었어요..! 고전문학을 좋아하는데\n" +
                        "추천 부탁드려요\n" +
                        "\n" +
                        "제가 인상깊게 읽었던 고전문학은\n" +
                        "참을 수 없는 존재의 가벼움\n" +
                        "데미안\n" +
                        "인간의 굴레\n" +
                        "대지(펄벅)\n" +
                        "당장 생각 나는건 이러네요.\n" +
                        "\n" +
                        "저런 류의 고전 소설 추천해주실게 있다면 추천 부탁드립니다.",
                false,
                "예술",
                "O",
                "O",
                1123,
                LocalDate.of(2024,6,5),
                LocalDate.of(2024,6,5)

        );

        Board board26 = new Board(
                member6,
                "문학 장르 책 추천받아",
                "인문학만 읽다보니 문학도 같이 읽고싶은데\n" +
                        "\n" +
                        "혹시 추천해줄 책 있어?\n" +
                        "\n" +
                        "뭔가 옛날에는 인문학을 고집했는데 결국 다양하게 읽는게 좋을거 같다는 생각이 들어서.\n" +
                        "\n" +
                        "누구나 들어본 유명한것도 괜찮으니 남겨줘.",
                false,
                "예술",
                "O",
                "O",
                227,
                LocalDate.of(2024,6,5),
                LocalDate.of(2024,6,5)

        );

        Board board27 = new Board(
                member7,
                "인생 영화 머야?",
                "난 라라랜드\n" +
                        "가치관 운운해서 미안..",
                false,
                "예술",
                "O",
                "O",
                252,
                LocalDate.of(2024,6,5),
                LocalDate.of(2024,6,5)

        );

        Board board28 = new Board(
                member8,
                "넷플릭스 영화 추천",
                "지금 집에서 볼만한 넷플릭스 영화 추천해줘요ㅎㅎ",
                false,
                "예술",
                "O",
                "O",
                123,
                LocalDate.of(2024,6,5),
                LocalDate.of(2024,6,5)

        );

        Board board29 = new Board(
                member9,
                "공연",
                "9월 해외 뮤지션들 공연/ 10월 브아솔 공연\n" +
                        "\n" +
                        "친구들은 공연을 갈만큼 안좋아한다고\n" +
                        "억지로 가자고 하기도 미안하구\n" +
                        "\n" +
                        "가고 싶은데 같이 갈 사람이 없어..\n" +
                        "공연 메이트라도 새로 사겨야 하는걸까",
                false,
                "예술",
                "X",
                "O",
                665,
                LocalDate.of(2024,6,7),
                LocalDate.of(2024,6,7)

        );

        Board board30 = new Board(
                member10,
                "공연 취미",
                "특히 클래식 쪽으로 좋아하면서\n" +
                        "연주회 발레 오페라 연극\n" +
                        "골고루 다 좋아하고\n" +
                        "연주회도 다양한 악기 독주랑 실내악 관현악\n" +
                        "골고루 다 들으러 다니는\n" +
                        "예술 공연 장르 구분 없이 다 좋아하는 분 있나요?\n" +
                        "있다면 좋아하는 공연은 다 보러 다니시나요\n" +
                        "다니실 때 혼자 즐기시나요\n" +
                        "궁금\n" +
                        "\n" +
                        "대체로 특정 취향이 있어서 집중한다고 해야하나\n" +
                        "쟝르를 한정해서 즐기는 것 같고\n" +
                        "두루두루 다 다니는 사람은 드믄 것 같더라구요\n" +
                        "\n" +
                        "이렇게 골고루 다니면\n" +
                        "한달에 공연 네다섯은 보게 되니\n" +
                        "티켓값도 만만치 않기도 하고\n" +
                        "매주 주말 하루를 공연에 쓰게 되는데\n" +
                        "\n" +
                        "시간과 돈의 문제로 마음은 있으나\n" +
                        "가지 못하는 분들이 대다수 이려나요\n" +
                        "\n" +
                        "난 보고 듣는 거 다 좋아해서\n" +
                        "발레는 다 챙겨 보고\n" +
                        "연주회는 유명 연주자다 싶음 듣고\n" +
                        "듣고 싶은 악기 개인 연주회도 듣고\n" +
                        "관현악 연주회도 곡이나 테마 찾아 가고 있는데\n" +
                        "나처럼 깊게라기 보다\n" +
                        "얕고 넓게 즐기는 분들이 있나 궁금해서요\n" +
                        "\n" +
                        "친구 만드는 거 자체가 쉬운 일도 아닌데\n" +
                        "장르마다 친구 만들 수도 없고\n" +
                        "혼자 보면 뭔가 허한 느낌이고\n" +
                        "이런 취향은 드믄 것 같고 \uD83E\uDD14\n" +
                        "\n" +
                        "그나저나 7, 8월엔 공연이 많지 않네\n" +
                        "좀 쉴 겸 다행인가 싶기도 하지만\n" +
                        "왠지 허전 허전",
                false,
                "예술",
                "X",
                "O",
                852,
                LocalDate.of(2024,6,8),
                LocalDate.of(2024,6,8)

        );


        Board board31 = new Board(
                member1,
                "다들 돈 많이모으네",
                "어떻게 살아야 그렇게 모으는거야? ㅠ\n" +
                        "3후반이고 애1명이고 맞벌이 새전3억인데\n" +
                        "모으고아낀다고 해도 5천넘게는 못 모으겠던데......\n" +
                        "다들 대단하다\n" +
                        "처음엔 나도 생활을 바꿔야지 다짐 해보다가도 쉽게 바뀌지않더라. 퇴직 후에 짜장면 배달 해야할듯..",
                false,
                "경제",
                "O",
                "O",
                398,
                LocalDate.of(2024,6,9),
                LocalDate.of(2024,6,9)

        );

        Board board32 = new Board(
                member2,
                "돈 많은데 돈안쓰는 남자..",
                "차도 좋고 집도 좋고 집안도 좋고 돈도 많이벌고 모아둔거도 많고 부동산도 많고.. 회사사람 모두가 저사람 돈 많다고 함\n" +
                        "진짜 모르고 만났고 지금은 내 남친임\n" +
                        "근데 데이트할때 돈을 아껴......\n" +
                        "편의점에서 비싸다고 아이스크림 안사먹고 ..\n" +
                        "난 비싸더라도 깨끗한곳이 좋은데 숙박도 6이상 넘어가면 안자려고 하고... 식당에서 저녁 먹는거도....그냥 뭐 맨날 비싼음식보다 부대찌개.김치찌개.백반 반복..\n" +
                        "\n" +
                        "내가 씀씀이가 큰건 절대 아님. 남친이 이러니까.. 지금 데이트하는게 옛날에 학생때 연애 생각나서ㅠ ㅠ",
                false,
                "경제",
                "O",
                "O",
                365,
                LocalDate.of(2024,6,9),
                LocalDate.of(2024,6,9)

        );

        Board board33 = new Board(
                member3,
                "한화 주가",
                "한화는 큰 기업인데 왜 주가가 24000원밖에 안해요?\n" +
                        "\n" +
                        "삼성전자처럼 액면분할 한것도 아닌것같은데",
                false,
                "경제",
                "X",
                "O",
                42,
                LocalDate.of(2024,6,10),
                LocalDate.of(2024,6,10)

        );

        Board board34 = new Board(
                member4,
                "웹툰 주가 근황",
                "데이마켓\n" +
                        "9시부터 칼같이 20% 점프함\n" +
                        "슈퍼개미 전업투자자 기관투자자들 출근해서 띄웠나봄\n" +
                        "여기에 외국인 좀 붙으면 한번 더 날아갈지도...\n" +
                        "물론 난 쫄보라 안삼 ㅋㅋㅋㅋㅋㅋ",
                false,
                "경제",
                "O",
                "O",
                52,
                LocalDate.of(2024,6,11),
                LocalDate.of(2024,6,11)

        );

        Board board35 = new Board(
                member5,
                "지금 경제 박살남",
                "엔화가 망햇음\n" +
                        "\n" +
                        "올해안에 채권못팔거고\n" +
                        "\n" +
                        "미국이 팔면 가만안둘테니.....\n" +
                        "\n" +
                        "일본 개망함......\n" +
                        "\n" +
                        "중국도 망함",
                false,
                "경제",
                "O",
                "O",
                68,
                LocalDate.of(2024,6,12),
                LocalDate.of(2024,6,12)

        );

        Board board36 = new Board(
                member6,
                "경제 재앙온대",
                "샌프란시스코 부동산 폭락중이래\n" +
                        "\n" +
                        "그래도 한국부동산은 상승이지\n" +
                        "\n" +
                        "강남은 없어서 못사잖아 ㅎㅎㅎ\n" +
                        "\n" +
                        "영끄리형누나들 있으니까 ㅎㅎㅎ",
                false,
                "경제",
                "O",
                "O",
                13,
                LocalDate.of(2024,6,13),
                LocalDate.of(2024,6,13)

        );

        Board board37 = new Board(
                member7,
                "지금 경제 분위기 장난아니긴하네..;;",
                "미국이 신무기 개발 삽질하다 중러에 뒤쳐져서 사실상 자원부국들이 미국 패싱하는거 같아서 달러 패권 무너져가고있는거 같고.. 금리올린다는데도 달러를 얼마나 쳐찍어댔으면 1277원대냐 ?\n" +
                        "자원 수급이 불안해지면 당연히 수입위주인 우리나라는 첫빠따로 조져질거같은 느낌드네. 무슨 보고서 보니 중러맞짱뜨면 미국이 반신불수될정도고 다시회복하려해도 " +
                        "중국이 회복속도 빨리서 결국엔 태평양 먹히게된다던데 미국이 태평양 손떼버리면 우리나라 끝장날듯",
                false,
                "경제",
                "O",
                "O",
                75,
                LocalDate.of(2024,6,13),
                LocalDate.of(2024,6,13)

        );

        Board board38 = new Board(
                member8,
                "주식 알려줘",
                "삼전이랑 현차 가지고 있는데\n" +
                        "그냥 가지고 있는 게 맞지....???\n" +
                        "삼전 6만 현기차도 젤 비쌀때 들어간거야",
                false,
                "경제",
                "X",
                "O",
                23,
                LocalDate.of(2024,6,14),
                LocalDate.of(2024,6,14)

        );

        Board board39 = new Board(
                member9,
                "공무원이 예산낭비정책 만드는거 어디 신고 못함?",
                "아 민원받다가 미쳐버릴거같다\n" +
                        "\n" +
                        "중앙기관에서 실적만들려고\n" +
                        "현실에 맞지도않는 뻘짓해서\n" +
                        "민원인이 화난거라....\n" +
                        "나도 민원인 입장 이해되고\n" +
                        "미안하단 말 빼고는 할말이없는데;;;;\n" +
                        "\n" +
                        "어디신고못해?",
                false,
                "경제",
                "O",
                "O",
                321,
                LocalDate.of(2024,6,14),
                LocalDate.of(2024,6,14)

        );

        Board board40 = new Board(
                member10,
                "정부는 무슨 생각으로 이공계 의료계 둘다 공격하는걸까?",
                "r&d 예산삭감으로 이공계 민심도 나락가고\n" +
                        "\n" +
                        "의대증원으로 의사민심도 나락가고\n" +
                        "\n" +
                        "적만 만드는 정책같은데 왜 총선직전에 이러는지 모르겠다",
                false,
                "경제",
                "O",
                "O",
                888,
                LocalDate.of(2024,6,15),
                LocalDate.of(2024,6,15)

        );






    }

}
