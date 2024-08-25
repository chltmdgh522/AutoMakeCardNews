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

        Board board41 = new Board(
                member1,
                "우주 경쟁",
                "그냥 문득 예전의 우주에 가겠다는 저의 꿈이 생각이 나 글 써보네요.\n" +
                        "한국의 우주 경쟁이 얼만큼 올라와있을까요..?\n" +
                        "우주에 대한 흥미와 관심 만으런 되지 않겠죠. 하지만 다양한 분야에서의 전문성이 있는 사람들이 함께 이루어 내야할 일은 아닐까요? 흔히 말하는 오타구만 모여서는 될 일이 아니지 않을까요?\n" +
                        "특히, 없던 새로운 개발이 필요한 부분은 더 다양한 생각을 가진 사람이 필요하지 않을까요?\n" +
                        "다른 분야로부터 발전된 기술이 적용되는 사례를 많아 보았는데, 물론 우주개발 분야는 다를 수 있겠지만 분명 다양한 분야의 아이디어가 큰 멋진 그림을 그릴 수 있을거란 생각이 듭니다.\n" +
                        "\n" +
                        "어디든 그 분야에서의 만의 꼰대(?) 문화를 버리고 다양한, 분야와의 융합이 필요하다 생각이 드네요.\n" +
                        "\n" +
                        "대한민국 천문인 화이팅!!\n" +
                        "\n" +
                        "우주 분야도 민간으로 넘어가는 시점에 일론 머스크 형님 처럼 큰 꿈을 가지신 분 있습니까!!?",
                false,
                "과학",
                "X",
                "O",
                112,
                LocalDate.of(2024,6,17),
                LocalDate.of(2024,6,17)

        );
        Board board42 = new Board(
                member2,
                "마시멜로 실험",
                "우리엄마가 나 4살때\n" +
                        "마시멜로 실험을 해봤대\n" +
                        "사탕 하나를 주고 15분 참으면\n" +
                        "하나더주겠다 했는데\n" +
                        "내가 \"엄마 난 두개필요없어요\"하면서\n" +
                        "참는 시늉도안하고\n" +
                        "그자리에서 홀랑 까먹었다네?\n" +
                        "이거 자기조절능력 부족한걸까ㅠㅜ\n" +
                        "지금도 딱히 물욕이 없는사람이긴해",
                false,
                "과학",
                "O",
                "O",
                341,
                LocalDate.of(2024,6,18),
                LocalDate.of(2024,6,18)

        );
        Board board43 = new Board(
                member3,
                "외계인",
                "외계문명에 관심있는 사람 있어?\n" +
                        "난 믿음 ㅋ\n" +
                        "지구까지 어떻게 오는지 너무 신기하",
                false,
                "과학",
                "X",
                "O",
                35,
                LocalDate.of(2024,6,19),
                LocalDate.of(2024,6,19)

        );

        Board board44 = new Board(
                member4,
                "지구...?",
                "지구 왜 멸망하지 않을까\n" +
                        "머지않은거같은데...",
                false,
                "과학",
                "X",
                "O",
                32,
                LocalDate.of(2024,6,19),
                LocalDate.of(2024,6,19)

        );

        Board board45 = new Board(
                member5,
                "옛날 과학자 VS 요즘과학자",
                "친구가 선생인데 별같잖은 이상한 초딩유튜브보고와서 진짜 말도 안되는 질문을 하긴한다더라",
                false,
                "과학",
                "O",
                "O",
                72,
                LocalDate.of(2024,6,20),
                LocalDate.of(2024,6,20)

        );

        Board board46 = new Board(
                member6,
                "R&D 예산삭감 = 저급 과학자 육성",
                "정출연은 고급인재들이 일하기 참 어려워..\n" +
                        "\n" +
                        "공공기관이랑 같이 묶어서 방만 경영이라니..\n" +
                        "미국 NASA가 1년에 20조를 먹어치는 곳이야..\n" +
                        "우리나라에 있었으면 이미 조직 해체 됐을거야\n" +
                        "\n" +
                        "어느정부나 과학기술정책을 혁신한다고 하자나?\n" +
                        "그럼 잘하는 사람은 키워주고\n" +
                        "방만한 연구자는 짜르고..\n" +
                        "이게 그렇게 어려울까...\n" +
                        "\n" +
                        "이걸 안하고 그냥 예산 깎아서 살충제 뿌리듯 모조리 죽이네..\n" +
                        "당연히 방만한 연구자는 어짜피 일 대충할거고 이직도 못하니 계속 연구소에 남아있을거고..안짤리니..세금으로..편히 놀겠고..\n" +
                        "선의의 연구자는 옆에 나쁜놈 때문에 자기도 같이 시달리니까 이런 대한민국의 과학환경에 한숨 푹푹쉬다 이직해버리지..이렇게 예산으로 막 옥죄기만하면 결국 연구소에 남는건 실력없는 넘들이야..\n" +
                        "\n" +
                        "국가연구소를 말도 안되게 공공기관으로 묶어놔서\n" +
                        "공공기관 방만 경영이라는 이상한 프레임 씌우고..\n" +
                        "카이스트도 공공기관이였는데..올해 겨우 빠져나왔지..\n" +
                        "\n" +
                        "여기서 일하는데 자부심이 생길수가 없어..\n" +
                        "차세대 원자력? 남아있는 저급 인재들이 하겠지..\n" +
                        "\n" +
                        "혁신이 아니라 실제로는 그냥 잘하는 넘들 죽이고 내보내고 다 썩은 놈들만 찌꺼기처럼 남겨두는 저급 필터링 정책 밖에 안돼..\n" +
                        "\n" +
                        "이번정부만 아니라 대한민국 과학기술정책은 항상 이랬음..오죽하면 박정희 시절의 과학정책이 그리워진다니까..그땐 대전 연구단지 조성해서..사택도 주고 서울대 교수 연봉보다 3배나 많이 주며 과학자 대우 해줬지..지금의 카이스트도 박정희 정부 작품이야..\n" +
                        "그런데 지금은 그냥 세금 도둑놈 취급만하고..1970년대 씨를 잘뿌려놓으니 지금 얼마나 잘 살아..한강의 기적을 이뤘는데..\n" +
                        "\n" +
                        "제발 좀 그냥 예산만 막 깎지마..예산 확보하기 제일 쉬운 방법인거 아는데 이제 이렇게는 고만하자..\n" +
                        "돈 떼먹고 일 안하는 넘들을 잡아..\n" +
                        "\n" +
                        "그냥 국민도 정부도 카르텔 프레임 씌워서 과학계 뭍어버릴려는듯..정말 간첩이 많은 것 같아서 슬프네..",
                false,
                "과학",
                "O",
                "O",
                754,
                LocalDate.of(2024,6,20),
                LocalDate.of(2024,6,20)

        );

        Board board47 = new Board(
                member7,
                "아인슈타인",
                "인류에 준 전체적인 영향력은\n" +
                        "\n" +
                        "+일까? - 일까?",
                false,
                "과학",
                "X",
                "O",
                91,
                LocalDate.of(2024,6,21),
                LocalDate.of(2024,6,21)

        );

        Board board48 = new Board(
                member8,
                "한국 기초과학 연구 수준",
                "볼수록 한국 사람들 참 똑똑하고, 열심히 일하고 평균적으로 사람들 능력있는 나라인데, 거기에 반해서 기초 과학 기술 수준이 너무 떨어지는 듯… 장기간으로 봤을 때 과학 기술력이 국가 경쟁력에 굉장히 중요한 부분일텐데 아쉬워. 큰 요소들 꽂아보자면 아래 정도인가.\n" +
                        "\n" +
                        "1. 이과생들 다 의사만 하려고 함\n" +
                        "2. 국내 인재들 다 해외로 도망가서 브레인 드레인 현상 심함\n" +
                        "3. 국가 지원 효율화 떨어지고 기부 문화가 덜 발달해 연구 진행할 재정 부족\n" +
                        "\n" +
                        "어떻게 하면 좀 나아질 수 있을까. 우리도 좀 제대로 된 노벨상도 받고 그래야지.",
                false,
                "과학",
                "O",
                "O",
                132,
                LocalDate.of(2024,6,21),
                LocalDate.of(2024,6,21)

        );

        Board board49 = new Board(
                member9,
                "생각보다 우리나라 기초과학쪽은 약한듯",
                "공단에서 가스사용시 예를들어 99.999%쓰던거를 99.9999%가스로 바꾸랫거든 업무편람이 바뀌었던가?\n" +
                        "그래서 부랴부랴 업체수배해봐도 없음...\n" +
                        "혹은 간혹 한두군데잇어도 전국 사업장, 공장물량 못맞추더라고.,\n" +
                        "\n" +
                        "민원폭탄맞고 다시 원상복귀...",
                false,
                "과학",
                "O",
                "O",
                823,
                LocalDate.of(2024,6,22),
                LocalDate.of(2024,6,22)

        );

        Board board50 = new Board(
                member10,
                "지구과학? 화학? 질문인데..?",
                "\n" +
                        "토픽 블라블라\n" +
                        "지구과학? 화학? 질문인데..\n" +
                        "서울특별시교육청\n" +
                        "· ʕ******\n" +
                        "작성일어제 조회수266 댓글24북마크 메뉴 더보기\n" +
                        "궁금해서 여기 여쭤봅니당.\n" +
                        "\n" +
                        "석유를 온도별로 증류? 가열?시키면\n" +
                        "아스팔트 경유 등유 휘발유 등등 나눠진다는건 어릴때 배운거같은데\n" +
                        "\n" +
                        "유전들 마다 석유를 끄잡아내면\n" +
                        "어떤석유는 휘발유가 더많이나오고\n" +
                        "어떤건 등유가 많이 나온다던지\n" +
                        "\n" +
                        "유전별 석유의 차이?가 있나용?\n" +
                        "\n" +
                        "그게있으면 20세기에 A지점석유는 등유가많았는데 21세기들어서 휘발유가많아졌다던가...\n" +
                        "\n" +
                        "아니라면 전세계 석유가 다 똑같은 품질의 놈들인건가여",
                false,
                "과학",
                "O",
                "O",
                24,
                LocalDate.of(2024,6,22),
                LocalDate.of(2024,6,22)

        );


        //카드뉴스
        CardNews cardNews1 = new CardNews(
                member1,
                "사과 가격 폭락",
                "사과 가격이.....",
                "X",
                "X",
                0L,
                "사과4.json",
                "사과4.png",
                "사과4.png",
                "경제"

                );

        CardNews cardNews2 = new CardNews(
                member2,
                "다같이 모여서 개발하자",
                "개발자의 숙명",
                "X",
                "X",
                0L,
                "IT.json",
                "IT.png",
                "IT.png",
                "IT"

        );

        CardNews cardNews3 = new CardNews(
                member3,
                "개발자",
                "멋있지 않아요??",
                "X",
                "X",
                0L,
                "개발자.json",
                "개발자.png",
                "개발자.png",
                "IT"

        );

        CardNews cardNews4 = new CardNews(
                member4,
                "개발자의 미래",
                "전망이 안좋아여",
                "X",
                "X",
                0L,
                "개발자2.json",
                "개발자2.png",
                "개발자2.png",
                "IT"

        );

        CardNews cardNews5 = new CardNews(
                member5,
                "경복궁",
                "경복궁의 아름다움",
                "X",
                "X",
                0L,
                "경복궁.json",
                "경복궁.png",
                "경복궁.png",
                "예술"

        );

        CardNews cardNews6 = new CardNews(
                member6,
                "재밌는 실험",
                "과학은 재밌어",
                "X",
                "X",
                0L,
                "과학.json",
                "과학.png",
                "과학.png",
                "과학"

        );

        CardNews cardNews7 = new CardNews(
                member7,
                "광화문",
                "조선의 유적",
                "X",
                "X",
                0L,
                "광화문.json",
                "광화문.png",
                "광화문.png",
                "예술"

        );

        CardNews cardNews8 = new CardNews(
                member8,
                "대한민국 경제",
                "맨날 안좋음..",
                "X",
                "X",
                0L,
                "대한민국 경제.json",
                "대한민국 경제.png",
                "대한민국 경제.png",
                "경제"

        );

        CardNews cardNews9 = new CardNews(
                member9,
                "도지코인",
                "사지 마삼",
                "X",
                "X",
                0L,
                "도지.json",
                "도지.png",
                "도지.png",
                "경제"

        );

        CardNews cardNews10 = new CardNews(
                member10,
                "라인아 가지마...",
                "일본한테 뺏기고 있는 라인..",
                "X",
                "X",
                0L,
                "라인.json",
                "라인.png",
                "라인.png",
                "경제"

        );

        CardNews cardNews11 = new CardNews(
                member10,
                "페이커가 하는거",
                "롤로로롤!",
                "X",
                "X",
                0L,
                "롤.json",
                "롤.png",
                "롤.png",
                "스포츠"

        );
        CardNews cardNews12 = new CardNews(
                member9,
                "대한민국 최고로 높은탑",
                "엄청 높다.....",
                "X",
                "X",
                0L,
                "롯데타워.json",
                "롯데타워.png",
                "롯데타워.png",
                "경제"

        );
        CardNews cardNews13 = new CardNews(
                member8,
                "문화재 강탈 X",
                "이걸 봐라!!",
                "X",
                "X",
                0L,
                "문화재.json",
                "문화재.png",
                "문화재.png",
                "예술"

        );
        CardNews cardNews14 = new CardNews(
                member7,
                "불국사의 아름다움",
                "불국사는 멋있다...",
                "X",
                "X",
                0L,
                "불국사.json",
                "불국사.png",
                "불국사.png",
                "예술"

        );
        CardNews cardNews15 = new CardNews(
                member6,
                "비트코인",
                "비트코인은 장기적으로 볼때 좋음",
                "X",
                "X",
                0L,
                "비트코인.json",
                "비트코인.png",
                "비트코인.png",
                "경제"

        );
        CardNews cardNews16 = new CardNews(
                member5,
                "신라의 유적",
                "석굴암의 역사",
                "X",
                "X",
                0L,
                "석굴암.json",
                "석굴암.png",
                "석굴암.png",
                "예술"

        );
        CardNews cardNews17 = new CardNews(
                member4,
                "손흥민은 아시아 최고 선수",
                "너무 멋있어요우ㅜㅜㅜㅜ",
                "X",
                "X",
                0L,
                "손흥민.json",
                "손흥민.png",
                "손흥민.png",
                "스포츠"

        );
        CardNews cardNews18 = new CardNews(
                member3,
                "수소선박",
                "잘 운행하길",
                "X",
                "X",
                0L,
                "수소선박.json",
                "수소선박.png",
                "수소선박.png",
                "경제"

        );
        CardNews cardNews19 = new CardNews(
                member2,
                "쟝고보단 스프링",
                "스프링 최고",
                "X",
                "X",
                0L,
                "스프링 프레임워크.json",
                "스프링 프레임워크.png",
                "스프링 프레임워크.png",
                "IT"

        );
        CardNews cardNews20 = new CardNews(
                member1,
                "한국 아시안컵 4강에서 멈춰...",
                "회장 사임해라",
                "X",
                "X",
                0L,
                "아시안컵.json",
                "아시안컵.png",
                "아시안컵.png",
                "스포츠"

        );
        CardNews cardNews21 = new CardNews(
                member1,
                "대한민국 최고 걸그룹 아일릿!",
                "이젠 에스파 ㅂㅇ",
                "X",
                "X",
                0L,
                "아일릿1.json",
                "아일릿1.png",
                "아일릿1.png",
                "예술"

        );CardNews cardNews22 = new CardNews(
                member2,
                "LG 야구 우승",
                "오홍",
                "X",
                "X",
                0L,
                "야구1.json",
                "야구1.png",
                "야구1.png",
                "스포츠"

        );CardNews cardNews23 = new CardNews(
                member3,
                "엔비디아 사세요!!!",
                "엔비디아 후딱 사셈",
                "X",
                "X",
                0L,
                "엔비디아.json",
                "엔비디아.png",
                "엔비디아.png",
                "경제"

        );
        CardNews cardNews24 = new CardNews(
                member4,
                "외계인의 존재",
                "외계인은 과연 있을까",
                "X",
                "X",
                0L,
                "외계인.json",
                "외계인.png",
                "외계인.png",
                "과학"

        );
        CardNews cardNews25 = new CardNews(
                member5,
                "유적 발굴",
                "고대 유적을 발굴했어여",
                "X",
                "X",
                0L,
                "유적.json",
                "유적.png",
                "유적.png",
                "예술"

        );
        CardNews cardNews26 = new CardNews(
                member6,
                "이더리움 사세요",
                "돈 많이 벌음",
                "X",
                "X",
                0L,
                "이더리움.json",
                "이더리움.png",
                "이더리움.png",
                "경제"

        );
        CardNews cardNews27 = new CardNews(
                member7,
                "일본문화",
                "사무라이들",
                "X",
                "X",
                0L,
                "일본 문화.json",
                "일본 문화.png",
                "일본 문화.png",
                "예술"

        );
        CardNews cardNews28 = new CardNews(
                member8,
                "대한민국 세계 꼴찌",
                "0.71..... 이게 맞나",
                "X",
                "X",
                0L,
                "저출산.json",
                "저출산.png",
                "저출산.png",
                "경제"

        );CardNews cardNews29 = new CardNews(
                member9,
                "전주한옥마을로 놀러오세요",
                "대한민국 최고의 한옥마을",
                "X",
                "X",
                0L,
                "전주한옥마을.json",
                "전주한옥마을.png",
                "전주한옥마을.png",
                "예술"

        );
        CardNews cardNews30 = new CardNews(
                member10,
                "우리나라 주가",
                "흠 주가의 미래는?",
                "X",
                "X",
                0L,
                "주가.json",
                "주가.png",
                "주가.png",
                "경제"

        );
        CardNews cardNews31 = new CardNews(
                member8,
                "이슈난 초전도체",
                "과연 고려대의 희망은?",
                "X",
                "X",
                0L,
                "초전도체.json",
                "초전도체.png",
                "초전도체.png",
                "과학"

        );

        CardNews cardNews32 = new CardNews(
                member3,
                "크리스마스 풍경",
                "크리스마스의 풍경은 멋지다.",
                "X",
                "X",
                0L,
                "크리스마스 풍경.json",
                "크리스마스 풍경.png",
                "크리스마스 풍경.png",
                "예술"

        );

        CardNews cardNews33 = new CardNews(
                member2,
                "테슬라 자동차",
                "멋있다....",
                "X",
                "X",
                0L,
                "테슬라.json",
                "테슬라.png",
                "테슬라.png",
                "IT"

        );
        CardNews cardNews34 = new CardNews(
                member2,
                "트와이스 최고",
                "걸그룹 트와이스!!!",
                "X",
                "X",
                0L,
                "트와이스1.json",
                "트와이스1.png",
                "트와이스1.png",
                "예술"

        );

        CardNews cardNews35 = new CardNews(
                member8,
                "프로그래밍에 관한 뉴스",
                "프로그래밍의 내용입니다.",
                "X",
                "X",
                0L,
                "프로그래밍.json",
                "프로그래밍.png",
                "프로그래밍.png",
                "IT"

        );

        CardNews cardNews36 = new CardNews(
                member8,
                "SK 하이닉스",
                "하이닉스에 관한 카드뉴스",
                "X",
                "X",
                0L,
                "하이닉스.json",
                "하이닉스.png",
                "하이닉스.png",
                "경제"

        );
        CardNews cardNews37 = new CardNews(
                member6,
                "우체국 업무",
                "우체국 업무에 관한 뉴스",
                "X",
                "X",
                0L,
                "우체국.json",
                "우체국.png",
                "우체국.png",
                "경제"

        );
        CardNews cardNews38 = new CardNews(
                member4,
                "헬스의 재미",
                "헬스 살리기",
                "X",
                "X",
                0L,
                "헬스.json",
                "헬스.png",
                "헬스.png",
                "스포츠"

        );




        //댓글
        Comment comment1 =new Comment(
                member1,
                board1,
                "좋은 글 감사해요!"
        );
        Comment comment2 =new Comment(
                member2,
                board1,
                "글 솜씨가 멋있습니다!"
        );
        Comment comment3 =new Comment(
                member3,
                board2,
                "좋은 글 감사해요!"
        );
        Comment comment4 =new Comment(
                member4,
                board2,
                "글 솜씨가 멋있습니다!"
        );
        Comment comment5 =new Comment(
                member5,
                board3,
                "좋은 글 감사해요!"
        );
        Comment comment6 =new Comment(
                member6,
                board3,
                "글 솜씨가 멋있습니다!"
        );Comment comment7 =new Comment(
                member7,
                board4,
                "좋은 글 감사해요!"
        );
        Comment comment8 =new Comment(
                member8,
                board4,
                "글 솜씨가 멋있습니다!"
        );
        Comment comment9 =new Comment(
                member9,
                board5,
                "좋은 글 감사해요!"
        );
        Comment comment10 =new Comment(
                member10,
                board5,
                "글 솜씨가 멋있습니다!"
        );



        Comment comment11 =new Comment(
                member1,
                board11,
                "좋은 글 감사해요!"
        );
        Comment comment12 =new Comment(
                member2,
                board11,
                "글 솜씨가 멋있습니다!"
        );
        Comment comment13 =new Comment(
                member3,
                board12,
                "좋은 글 감사해요!"
        );
        Comment comment14 =new Comment(
                member4,
                board12,
                "글 솜씨가 멋있습니다!"
        );
        Comment comment15 =new Comment(
                member5,
                board13,
                "좋은 글 감사해요!"
        );
        Comment comment16 =new Comment(
                member6,
                board13,
                "글 솜씨가 멋있습니다!"
        );Comment comment17 =new Comment(
                member7,
                board14,
                "좋은 글 감사해요!"
        );
        Comment comment18 =new Comment(
                member8,
                board14,
                "글 솜씨가 멋있습니다!"
        );
        Comment comment19 =new Comment(
                member9,
                board15,
                "좋은 글 감사해요!"
        );
        Comment comment20 =new Comment(
                member10,
                board15,
                "글 솜씨가 멋있습니다!"
        );


        Comment comment21 =new Comment(
                member1,
                board21,
                "좋은 글 감사해요!"
        );
        Comment comment22 =new Comment(
                member2,
                board21,
                "글 솜씨가 멋있습니다!"
        );
        Comment comment23 =new Comment(
                member3,
                board22,
                "좋은 글 감사해요!"
        );
        Comment comment24 =new Comment(
                member4,
                board22,
                "글 솜씨가 멋있습니다!"
        );
        Comment comment25 =new Comment(
                member5,
                board23,
                "좋은 글 감사해요!"
        );
        Comment comment26 =new Comment(
                member6,
                board23,
                "글 솜씨가 멋있습니다!"
        );Comment comment27 =new Comment(
                member7,
                board24,
                "좋은 글 감사해요!"
        );
        Comment comment28 =new Comment(
                member8,
                board24,
                "글 솜씨가 멋있습니다!"
        );
        Comment comment29 =new Comment(
                member9,
                board25,
                "좋은 글 감사해요!"
        );
        Comment comment30 =new Comment(
                member10,
                board25,
                "글 솜씨가 멋있습니다!"
        );

        Comment comment31 =new Comment(
                member1,
                board31,
                "좋은 글 감사해요!"
        );
        Comment comment32 =new Comment(
                member2,
                board31,
                "글 솜씨가 멋있습니다!"
        );
        Comment comment33 =new Comment(
                member3,
                board32,
                "좋은 글 감사해요!"
        );
        Comment comment34 =new Comment(
                member4,
                board32,
                "글 솜씨가 멋있습니다!"
        );
        Comment comment35 =new Comment(
                member5,
                board33,
                "좋은 글 감사해요!"
        );
        Comment comment36 =new Comment(
                member6,
                board33,
                "글 솜씨가 멋있습니다!"
        );Comment comment37 =new Comment(
                member7,
                board34,
                "좋은 글 감사해요!"
        );
        Comment comment38 =new Comment(
                member8,
                board34,
                "글 솜씨가 멋있습니다!"
        );
        Comment comment39 =new Comment(
                member9,
                board35,
                "좋은 글 감사해요!"
        );
        Comment comment40 =new Comment(
                member10,
                board35,
                "글 솜씨가 멋있습니다!"
        );

        Comment comment41 =new Comment(
                member1,
                board41,
                "좋은 글 감사해요!"
        );
        Comment comment42 =new Comment(
                member2,
                board41,
                "글 솜씨가 멋있습니다!"
        );
        Comment comment43 =new Comment(
                member3,
                board42,
                "좋은 글 감사해요!"
        );
        Comment comment44 =new Comment(
                member4,
                board42,
                "글 솜씨가 멋있습니다!"
        );
        Comment comment45 =new Comment(
                member5,
                board43,
                "좋은 글 감사해요!"
        );
        Comment comment46 =new Comment(
                member6,
                board43,
                "글 솜씨가 멋있습니다!"
        );Comment comment47 =new Comment(
                member7,
                board44,
                "좋은 글 감사해요!"
        );
        Comment comment48 =new Comment(
                member8,
                board44,
                "글 솜씨가 멋있습니다!"
        );
        Comment comment49 =new Comment(
                member9,
                board45,
                "좋은 글 감사해요!"
        );
        Comment comment50 =new Comment(
                member10,
                board45,
                "글 솜씨가 멋있습니다!"
        );


        Comment comment51 =new Comment(
                member1,
                board6,
                "좋은 글 감사해요!"
        );
        Comment comment52 =new Comment(
                member2,
                board6,
                "글 솜씨가 멋있습니다!"
        );
        Comment comment53 =new Comment(
                member3,
                board7,
                "좋은 글 감사해요!"
        );
        Comment comment54 =new Comment(
                member4,
                board7,
                "글 솜씨가 멋있습니다!"
        );
        Comment comment55 =new Comment(
                member5,
                board8,
                "좋은 글 감사해요!"
        );
        Comment comment56 =new Comment(
                member6,
                board8,
                "글 솜씨가 멋있습니다!"
        );Comment comment57 =new Comment(
                member7,
                board9,
                "좋은 글 감사해요!"
        );
        Comment comment58 =new Comment(
                member8,
                board9,
                "글 솜씨가 멋있습니다!"
        );
        Comment comment59 =new Comment(
                member9,
                board10,
                "좋은 글 감사해요!"
        );
        Comment comment60 =new Comment(
                member10,
                board10,
                "글 솜씨가 멋있습니다!"
        );

        Comment comment61 =new Comment(
                member1,
                board16,
                "좋은 글 감사해요!"
        );
        Comment comment62 =new Comment(
                member2,
                board16,
                "글 솜씨가 멋있습니다!"
        );
        Comment comment63 =new Comment(
                member3,
                board17,
                "좋은 글 감사해요!"
        );
        Comment comment64 =new Comment(
                member4,
                board17,
                "글 솜씨가 멋있습니다!"
        );
        Comment comment65 =new Comment(
                member5,
                board18,
                "좋은 글 감사해요!"
        );
        Comment comment66 =new Comment(
                member6,
                board18,
                "글 솜씨가 멋있습니다!"
        );Comment comment67 =new Comment(
                member7,
                board19,
                "좋은 글 감사해요!"
        );
        Comment comment68 =new Comment(
                member8,
                board19,
                "글 솜씨가 멋있습니다!"
        );
        Comment comment69 =new Comment(
                member9,
                board20,
                "좋은 글 감사해요!"
        );
        Comment comment70 =new Comment(
                member10,
                board20,
                "글 솜씨가 멋있습니다!"
        );

        Comment comment71 =new Comment(
                member1,
                board26,
                "좋은 글 감사해요!"
        );
        Comment comment72 =new Comment(
                member2,
                board26,
                "글 솜씨가 멋있습니다!"
        );
        Comment comment73 =new Comment(
                member3,
                board27,
                "좋은 글 감사해요!"
        );
        Comment comment74 =new Comment(
                member4,
                board27,
                "글 솜씨가 멋있습니다!"
        );
        Comment comment75 =new Comment(
                member5,
                board28,
                "좋은 글 감사해요!"
        );
        Comment comment76 =new Comment(
                member6,
                board28,
                "글 솜씨가 멋있습니다!"
        );Comment comment77 =new Comment(
                member7,
                board29,
                "좋은 글 감사해요!"
        );
        Comment comment78 =new Comment(
                member8,
                board29,
                "글 솜씨가 멋있습니다!"
        );
        Comment comment79 =new Comment(
                member9,
                board30,
                "좋은 글 감사해요!"
        );
        Comment comment80 =new Comment(
                member10,
                board30,
                "글 솜씨가 멋있습니다!"
        );

        Comment comment81 =new Comment(
                member1,
                board36,
                "좋은 글 감사해요!"
        );
        Comment comment82 =new Comment(
                member2,
                board36,
                "글 솜씨가 멋있습니다!"
        );
        Comment comment83 =new Comment(
                member3,
                board37,
                "좋은 글 감사해요!"
        );
        Comment comment84 =new Comment(
                member4,
                board37,
                "글 솜씨가 멋있습니다!"
        );
        Comment comment85 =new Comment(
                member5,
                board38,
                "좋은 글 감사해요!"
        );
        Comment comment86 =new Comment(
                member6,
                board38,
                "글 솜씨가 멋있습니다!"
        );Comment comment87 =new Comment(
                member7,
                board39,
                "좋은 글 감사해요!"
        );
        Comment comment88 =new Comment(
                member8,
                board39,
                "글 솜씨가 멋있습니다!"
        );
        Comment comment89 =new Comment(
                member9,
                board40,
                "좋은 글 감사해요!"
        );
        Comment comment90 =new Comment(
                member10,
                board40,
                "글 솜씨가 멋있습니다!"
        );

        Comment comment91 =new Comment(
                member1,
                board46,
                "좋은 글 감사해요!"
        );
        Comment comment92 =new Comment(
                member2,
                board46,
                "글 솜씨가 멋있습니다!"
        );
        Comment comment93 =new Comment(
                member3,
                board47,
                "좋은 글 감사해요!"
        );
        Comment comment94 =new Comment(
                member4,
                board47,
                "글 솜씨가 멋있습니다!"
        );
        Comment comment95 =new Comment(
                member5,
                board48,
                "좋은 글 감사해요!"
        );
        Comment comment96 =new Comment(
                member6,
                board48,
                "글 솜씨가 멋있습니다!"
        );
        Comment comment97 =new Comment(
                member7,
                board49,
                "좋은 글 감사해요!"
        );
        Comment comment98 =new Comment(
                member8,
                board49,
                "글 솜씨가 멋있습니다!"
        );
        Comment comment99 =new Comment(
                member9,
                board50,
                "좋은 글 감사해요!"
        );
        Comment comment100 =new Comment(
                member10,
                board50,
                "글 솜씨가 멋있습니다!"
        );













        // 회원 정보 저장
        memberRepository.save(member0);
        memberRepository.save(member1);
        memberRepository.save(member2);
        memberRepository.save(member3);
        memberRepository.save(member4);
        memberRepository.save(member5);
        memberRepository.save(member6);
        memberRepository.save(member7);
        memberRepository.save(member8);
        memberRepository.save(member9);
        memberRepository.save(member10);


        //게시글 저장
        boardRepository.save(board1);
        boardRepository.save(board2);
        boardRepository.save(board3);
        boardRepository.save(board4);
        boardRepository.save(board5);
        boardRepository.save(board6);
        boardRepository.save(board7);
        boardRepository.save(board8);
        boardRepository.save(board9);
        boardRepository.save(board10);
        boardRepository.save(board11);
        boardRepository.save(board12);
        boardRepository.save(board13);
        boardRepository.save(board14);
        boardRepository.save(board15);
        boardRepository.save(board16);
        boardRepository.save(board17);
        boardRepository.save(board18);
        boardRepository.save(board19);
        boardRepository.save(board20);
        boardRepository.save(board21);
        boardRepository.save(board22);
        boardRepository.save(board23);
        boardRepository.save(board24);
        boardRepository.save(board25);
        boardRepository.save(board26);
        boardRepository.save(board27);
        boardRepository.save(board28);
        boardRepository.save(board29);
        boardRepository.save(board30);
        boardRepository.save(board31);
        boardRepository.save(board32);
        boardRepository.save(board33);
        boardRepository.save(board34);
        boardRepository.save(board35);
        boardRepository.save(board36);
        boardRepository.save(board37);
        boardRepository.save(board38);
        boardRepository.save(board39);
        boardRepository.save(board40);
        boardRepository.save(board41);
        boardRepository.save(board42);
        boardRepository.save(board43);
        boardRepository.save(board44);
        boardRepository.save(board45);
        boardRepository.save(board46);
        boardRepository.save(board47);
        boardRepository.save(board48);
        boardRepository.save(board49);
        boardRepository.save(board50);


        // 카드뉴스
        cardNewsRepository.save(cardNews1);
        cardNewsRepository.save(cardNews2);
        cardNewsRepository.save(cardNews3);
        cardNewsRepository.save(cardNews4);
        cardNewsRepository.save(cardNews5);
        cardNewsRepository.save(cardNews6);
        cardNewsRepository.save(cardNews7);
        cardNewsRepository.save(cardNews8);
        cardNewsRepository.save(cardNews9);
        cardNewsRepository.save(cardNews10);
        cardNewsRepository.save(cardNews11);
        cardNewsRepository.save(cardNews12);
        cardNewsRepository.save(cardNews13);
        cardNewsRepository.save(cardNews14);
        cardNewsRepository.save(cardNews15);
        cardNewsRepository.save(cardNews16);
        cardNewsRepository.save(cardNews17);
        cardNewsRepository.save(cardNews18);
        cardNewsRepository.save(cardNews19);
        cardNewsRepository.save(cardNews20);
        cardNewsRepository.save(cardNews21);
        cardNewsRepository.save(cardNews22);
        cardNewsRepository.save(cardNews23);
        cardNewsRepository.save(cardNews24);
        cardNewsRepository.save(cardNews25);
        cardNewsRepository.save(cardNews26);
        cardNewsRepository.save(cardNews27);
        cardNewsRepository.save(cardNews28);
        cardNewsRepository.save(cardNews29);
        cardNewsRepository.save(cardNews30);
        cardNewsRepository.save(cardNews31);
        cardNewsRepository.save(cardNews32);
        cardNewsRepository.save(cardNews33);
        cardNewsRepository.save(cardNews34);
        cardNewsRepository.save(cardNews35);
        cardNewsRepository.save(cardNews36);
        cardNewsRepository.save(cardNews37);
        cardNewsRepository.save(cardNews38);


        //댓글
        commentRepository.save(comment1);
        commentRepository.save(comment2);
        commentRepository.save(comment3);
        commentRepository.save(comment4);
        commentRepository.save(comment5);
        commentRepository.save(comment6);
        commentRepository.save(comment7);
        commentRepository.save(comment8);
        commentRepository.save(comment9);
        commentRepository.save(comment10);
        commentRepository.save(comment11);
        commentRepository.save(comment12);
        commentRepository.save(comment13);
        commentRepository.save(comment14);
        commentRepository.save(comment15);
        commentRepository.save(comment16);
        commentRepository.save(comment17);
        commentRepository.save(comment18);
        commentRepository.save(comment19);
        commentRepository.save(comment20);
        commentRepository.save(comment21);
        commentRepository.save(comment22);
        commentRepository.save(comment23);
        commentRepository.save(comment24);
        commentRepository.save(comment25);
        commentRepository.save(comment26);
        commentRepository.save(comment27);
        commentRepository.save(comment28);
        commentRepository.save(comment29);
        commentRepository.save(comment30);
        commentRepository.save(comment31);
        commentRepository.save(comment32);
        commentRepository.save(comment33);
        commentRepository.save(comment34);
        commentRepository.save(comment35);
        commentRepository.save(comment36);
        commentRepository.save(comment37);
        commentRepository.save(comment38);
        commentRepository.save(comment39);
        commentRepository.save(comment40);
        commentRepository.save(comment41);
        commentRepository.save(comment42);
        commentRepository.save(comment43);
        commentRepository.save(comment44);
        commentRepository.save(comment45);
        commentRepository.save(comment46);
        commentRepository.save(comment47);
        commentRepository.save(comment48);
        commentRepository.save(comment49);
        commentRepository.save(comment50);
        commentRepository.save(comment51);
        commentRepository.save(comment52);
        commentRepository.save(comment53);
        commentRepository.save(comment54);
        commentRepository.save(comment55);
        commentRepository.save(comment56);
        commentRepository.save(comment57);
        commentRepository.save(comment58);
        commentRepository.save(comment59);
        commentRepository.save(comment60);
        commentRepository.save(comment61);
        commentRepository.save(comment62);
        commentRepository.save(comment63);
        commentRepository.save(comment64);
        commentRepository.save(comment65);
        commentRepository.save(comment66);
        commentRepository.save(comment67);
        commentRepository.save(comment68);
        commentRepository.save(comment69);
        commentRepository.save(comment70);
        commentRepository.save(comment71);
        commentRepository.save(comment72);
        commentRepository.save(comment73);
        commentRepository.save(comment74);
        commentRepository.save(comment75);
        commentRepository.save(comment76);
        commentRepository.save(comment77);
        commentRepository.save(comment78);
        commentRepository.save(comment79);
        commentRepository.save(comment80);
        commentRepository.save(comment81);
        commentRepository.save(comment82);
        commentRepository.save(comment83);
        commentRepository.save(comment84);
        commentRepository.save(comment85);
        commentRepository.save(comment86);
        commentRepository.save(comment87);
        commentRepository.save(comment88);
        commentRepository.save(comment89);
        commentRepository.save(comment90);
        commentRepository.save(comment91);
        commentRepository.save(comment92);
        commentRepository.save(comment93);
        commentRepository.save(comment94);
        commentRepository.save(comment95);
        commentRepository.save(comment96);
        commentRepository.save(comment97);
        commentRepository.save(comment98);
        commentRepository.save(comment99);
        commentRepository.save(comment100);





    }

}
